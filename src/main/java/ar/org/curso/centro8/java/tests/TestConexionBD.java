package ar.org.curso.centro8.java.tests;

import java.sql.Connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestConexionBD {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();

        try (HikariDataSource ds = new HikariDataSource(config);
                Connection conn = ds.getConnection()) {
            if(conn.isValid(2)) {
                System.out.println("\n***********************************");
                System.out.println("Conexión exitosa a la base de datos " + conn.getMetaData().getURL());
                System.out.println("***********************************\n");
            } else {
                System.out.println("\n** ERROR ** No se pudo establecer una conexión válida con la BD " + conn.getMetaData().getURL());
            }
        } catch (Exception e) {
            System.out.println("ERROR al conectar con la base de datos: " + e.getMessage());
        }
    }
}
