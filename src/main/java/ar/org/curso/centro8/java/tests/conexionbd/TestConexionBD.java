package ar.org.curso.centro8.java.tests.conexionbd;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Clase para probar la conexión a una base de datos en la nube utilizando HikariCP.
 * Carga las propiedades de conexión desde el archivo "application.properties"
 * y establece una conexión a la base de datos configurada. Si la conexión es exitosa,
 * imprime un mensaje de éxito con la URL de la base de datos. Si ocurre un error 
 * o la conexión no es válida, imprime un mensaje de error.
 */
public class TestConexionBD {
    public static void main(String[] args) {
        Properties props = new Properties();
        try (InputStream in = TestConexionBD.class // obtenemos el objeto class de esta clase
                .getClassLoader() // obtiene la clase que carga las clases y los recursos
                .getResourceAsStream("application.properties")) { // busca el archivo que le pasamos como parámetro
            // y lo devuelve como un flujo de bytes
            if (in == null) {
                System.err.println("No se econtró el application.properties");
                return;
            }
            props.load(in);
            // cargamos todas las propiedades en el props, es decir las combinaciones
            // clave-valor
        } catch (Exception e) {
            System.err.println("Error cargando las properties: " + e.getMessage());
        }

        // configuramos el pool de conexiones de HikariCP
        HikariConfig config = new HikariConfig();
        // configuramos la URL
        config.setJdbcUrl(props.getProperty("spring.datasource.url"));
        // configuramos el usuario
        config.setUsername(props.getProperty("spring.datasource.username"));
        // configuramos el password
        config.setPassword(props.getProperty("spring.datasource.password"));

        // creamos el DataSource con el pool de conexiones y probamos la conexión
        try (HikariDataSource ds = new HikariDataSource(config);
                Connection conn = ds.getConnection()) { // obtenemos la conexión
            if (conn.isValid(2)) {
                System.out.println("\n*********************");
                System.out.println("Conexión exitosa a: " + conn.getMetaData().getURL());
                System.out.println("*********************\n");
                // getMetaData obtiene la información de la conexión
            } else {
                System.err.println("** ERROR ** La conexión no es válida");
            }
        } catch (Exception e) {
            System.err.println("No se pudo conectar " + e.getMessage());
        }

    }
}
