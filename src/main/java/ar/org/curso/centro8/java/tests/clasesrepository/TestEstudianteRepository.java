package ar.org.curso.centro8.java.tests.clasesrepository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.repositories.EstudianteRepository;
import ar.org.curso.centro8.java.tests.ConfiguracionBD;

public class TestEstudianteRepository {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();
        try (HikariDataSource ds = new HikariDataSource(config)) {
            EstudianteRepository estudianteRepository = new EstudianteRepository(ds);
            int idEstudiante = 1;
            int idGrado = 11;

            System.out.println("Prueba de impresión de un estudiante por ID: " + idEstudiante);
            Estudiante est = estudianteRepository.findById(idEstudiante);
            if (est != null) {
                System.out.println(est.getNombre() + " " + est.getApellido() + " tiene " + est.getEdad() + " años");
            } else {
                System.out.println("No se encontró el estudiante con ID " + idEstudiante);
            }

            System.out.println("\nPrueba de impresión de todos los estudiantes del grado con id " + idGrado);
            estudianteRepository.findByGrado(idGrado).forEach(e -> {
                System.out.println(e.getNombre() + " " + e.getApellido() + " tiene " + e.getEdad() + " años");
            });
        } catch (Exception e) {
            System.err.println("No se pudo conectar " + e.getMessage());
        }
    }
}
