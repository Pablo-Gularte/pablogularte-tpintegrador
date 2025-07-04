package ar.org.curso.centro8.java.tests.pruebaspropias.testrepositorios;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.models.entities.Asignatura;
import ar.org.curso.centro8.java.models.repositories.AsignaturaRepository;
import ar.org.curso.centro8.java.tests.pruebaspropias.ConfiguracionBD;

public class TestAsignaturaRepository {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();

        try (HikariDataSource ds = new HikariDataSource(config)) {
            AsignaturaRepository asignaturaRepository = new AsignaturaRepository(ds);
            int idAsignatura = 1;

            Asignatura asignatura = asignaturaRepository.findById(idAsignatura);
            if (asignatura != null) {
                System.out.println("Prueba de impresión de una asignatura por ID: " + idAsignatura);
                System.out.println(asignatura.getNombreAsignatura() + " - Docente: " + asignatura.getDocente());
            } else {
                System.out.println("No se encontró la asignatura con ID " + idAsignatura);
            }
            System.out.println();
            System.out.println("Imprimo todas las asignaturas con los respesctivos docentes:");
            for (Asignatura a : asignaturaRepository.findAll()) {
                System.out.println(a.getNombreAsignatura() + " - Docente: " + a.getDocente());
            }
        } catch (Exception e) {
            System.out.println("ERROR al conectar con la base de datos: " + e.getMessage());
        }
    }
}
