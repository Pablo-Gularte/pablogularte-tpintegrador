package ar.org.curso.centro8.java.tests.clasesindividuales;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Grado;
import ar.org.curso.centro8.java.repositories.GradoRepository;
import ar.org.curso.centro8.java.tests.ConfiguracionBD;

public class TestGradoRepository {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();
        try (HikariDataSource ds = new HikariDataSource(config)) {
            GradoRepository gradoRepository = new GradoRepository(ds);
            
            int idGrado = 11;
            System.out.println("Prueba de impresión de un grado por ID: " + idGrado);
            Grado grado = gradoRepository.findById(idGrado);
            
            if (grado != null) {
                System.out.println(grado);
            } else {
                System.out.println("No se encontró el grado con ID " + idGrado);
            }

            String nombreGrado = "Sexto";
            System.out.println("Prueba de impresión de un grado por nombre: " + nombreGrado);
            Grado gradoPorNombre = gradoRepository.findByNombreGrado(nombreGrado);
            if (gradoPorNombre != null) {
                System.out.println(gradoPorNombre);
            } else {
                System.out.println("No se encontró el grado con nombre " + nombreGrado);
            }
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}
