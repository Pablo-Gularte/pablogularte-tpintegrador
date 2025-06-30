package ar.org.curso.centro8.java.tests.pruebaspropias;

import java.io.InputStream;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;

/**
 * Esta clase de utiliza para concentrar la lógica de conexión a la base de
 * datos utilizando HikariCP. Carga las propiedades de conexión desde el archivo
 * "application.properties" y devuelve una instancia de HikariConfig configurada
 * con los parámetros de conexión por medio de un mpétodo estático.
 * Este método facilita el acceso a la configuración de la base de datos
 * para las clases de prueba de los repositorios.
 */
public class ConfiguracionBD {
    private static Properties prop = new Properties();
    private static HikariConfig config = new HikariConfig();

    public static HikariConfig getConfiguracion() {
        try (InputStream in = ConfiguracionBD.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in != null) {
                prop.load(in);
                config.setJdbcUrl(prop.getProperty("spring.datasource.url"));
                config.setUsername(prop.getProperty("spring.datasource.username"));
                config.setPassword(prop.getProperty("spring.datasource.password"));
            } else {
                System.err.println("No se pudo cargar el archivo de configuración.");
            }
        } catch (Exception e) {
            System.err.println("Error cargando las properties: " + e.getMessage());
        }
        return config;
    }
}
