package ar.org.curso.centro8.java.tests.clasesdao;

import java.io.InputStream;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.repositories.EstudianteDAO;

public class TestEstudiante {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (InputStream in = TestEstudiante.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                props.load(in);
                HikariConfig config = new HikariConfig();
                config.setJdbcUrl(props.getProperty("spring.datasource.url"));
                config.setUsername(props.getProperty("spring.datasource.username"));
                config.setPassword(props.getProperty("spring.datasource.password"));

                try (HikariDataSource ds = new HikariDataSource(config)) {
                    EstudianteDAO estudianteDAO = new EstudianteDAO(ds);
                    int idEstudiante = 1;
                    int idGrado = 11;

                    System.out.println("Prueba de impresión de un estudiante por ID: " + idEstudiante);
                    Estudiante est = estudianteDAO.findById(idEstudiante);
                    if (est != null) {
                        System.out.println(est.getNombre() + " " + est.getApellido() + " tiene " + est.getEdad() + " años");
                    } else {
                        System.out.println("No se encontró el estudiante con ID " + idEstudiante);
                    }

                    System.out.println("\nPrueba de impresión de todos los estudiantes del grado con id " + idGrado);
                    estudianteDAO.findByGrado(idGrado).forEach(e -> {System.out.println(e.getNombre() + " " + e.getApellido() + " tiene " + e.getEdad() + " años");});
                } catch (Exception e) {
                    System.err.println("No se pudo conectar " + e.getMessage());
                }
            } else {
                System.err.println("No se pudo cargar el archivo de configuración.");
            }
            
        } catch (Exception e) {
            System.err.println("Error cargando las properties: " + e.getMessage());
        }
    }
}
