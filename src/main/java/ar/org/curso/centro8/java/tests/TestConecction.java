package ar.org.curso.centro8.java.tests;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestConecction {
    public static void main(String[] args) {
        Properties props = new Properties();

        try (InputStream in = TestConecction.class
                                            .getClassLoader()
                                            .getResourceAsStream("application.properties")) {
            if (in == null) {
                System.out.println("No se econtró el archivo application.properties.");
                return;
            }
            props.load(in);
        } catch (Exception e) {
            System.err.println("Error cargando las properties: " + e.getMessage());
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(props.getProperty("spring.datasource.url"));
        config.setUsername(props.getProperty("spring.datasource.username"));
        config.setPassword(props.getProperty("spring.datasource.password"));

        try (HikariDataSource ds = new HikariDataSource(config)) {
            Connection conn = ds.getConnection();
            
            if(conn.isValid(2)) {
                System.out.println("Conexión exitosa a la base de datos: " + conn.getMetaData().getURL());
            } else {
                System.out.println("## ATENCION ## No se pudo establecer la conexión a la base de datos.");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR al intentar conectar con la base de datos: " + e.getMessage());
        }
    }
}
