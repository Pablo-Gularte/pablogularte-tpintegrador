package ar.org.curso.centro8.java.tests.clasesdao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Asistencia;
import ar.org.curso.centro8.java.repositories.AsistenciaDAO;
import ar.org.curso.centro8.java.tests.ConfiguracionBD;

public class TestAsistenciaDAO {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();
        try (HikariDataSource ds = new HikariDataSource(config)) {
            AsistenciaDAO asistenciaDAO = new AsistenciaDAO(ds);
            int idAsistencia = 1; // Cambia este ID según tus datos
            Asistencia asistencia = asistenciaDAO.findById(idAsistencia);
            if (asistencia != null) {
                System.out.println("Prueba de impresión de una asistencia por ID: " + idAsistencia);
                System.out.println(asistencia);
            } else {
                System.out.println("No se encontró la asistencia con ID " + idAsistencia);
            }
            
        } catch (Exception e) {
            System.out.println("ERROR al conectar con la base de datos: " + e.getMessage());
        }
    }
}
