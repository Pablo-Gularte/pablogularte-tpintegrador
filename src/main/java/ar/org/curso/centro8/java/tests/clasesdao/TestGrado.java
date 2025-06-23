package ar.org.curso.centro8.java.tests.clasesdao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Grado;
import ar.org.curso.centro8.java.repositories.GradoDAO;

public class TestGrado {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();
        try (HikariDataSource ds = new HikariDataSource(config)) {
            GradoDAO gradoDAO = new GradoDAO(ds);
            int idGrado = 11;
            System.out.println("Prueba de impresión de un grado por ID: " + idGrado);
            Grado grado = gradoDAO.findById(idGrado);
            if(grado != null) {
                System.out.println(grado);
            } else {
                System.out.println("No se encontró el grado con ID " + idGrado);
            }
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}
