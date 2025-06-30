package ar.org.curso.centro8.java.tests.pruebaspropias.testrepositorios;

import java.time.LocalDate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Asistencia;
import ar.org.curso.centro8.java.enums.TipoAsistencia;
import ar.org.curso.centro8.java.repositories.AsistenciaRepository;
import ar.org.curso.centro8.java.tests.pruebaspropias.ConfiguracionBD;

public class TestAsistenciaRepository {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();
        try (HikariDataSource ds = new HikariDataSource(config)) {
            AsistenciaRepository asistenciaRepository = new AsistenciaRepository(ds);
            int idAsistencia = 1; // Cambia este ID según tus datos
            Asistencia asistencia = asistenciaRepository.findById(idAsistencia);
            if (asistencia != null) {
                System.out.println("Prueba de impresión de una asistencia por ID: " + idAsistencia);
                System.out.println(asistencia);
            } else {
                System.out.println("No se encontró la asistencia con ID " + idAsistencia);
            }
            
            // Creo un registro de asistencia
            Asistencia nuevaAsistencia = new Asistencia(0, LocalDate.now(), 1, TipoAsistencia.PRESENTE);
            asistenciaRepository.create(nuevaAsistencia);
            if (nuevaAsistencia.getIdAsistencia() > 0) {
                System.out.println("## SE CREO un nuevo registor de asistencia de ID: " + nuevaAsistencia.getIdAsistencia());
                System.out.println(nuevaAsistencia);
            } else {
                System.out.println("## ATENCIÓN ## NO se pudo crear el nuevo registro de asistencia.");
            }

            // Elimino el registro recién creado
            int filasAfectadas = asistenciaRepository.delete(nuevaAsistencia.getIdAsistencia());
            if (filasAfectadas > 0) {
                System.out.println("## Se ha eliminado el registro de asistencia de ID: " + nuevaAsistencia.getIdAsistencia());
            } else {
                System.out.println("## Atención ## No se pudo elimianr el registro de asistencia de ID: " + nuevaAsistencia.getIdAsistencia());
            }

        } catch (Exception e) {
            System.out.println("ERROR al conectar con la base de datos: " + e.getMessage());
        }
    }
}
