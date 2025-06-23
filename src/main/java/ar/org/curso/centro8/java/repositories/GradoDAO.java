package ar.org.curso.centro8.java.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.org.curso.centro8.java.entities.Grado;
import ar.org.curso.centro8.java.enums.Ciclo;
import ar.org.curso.centro8.java.enums.NombreGrado;
import ar.org.curso.centro8.java.enums.Turno;
import ar.org.curso.centro8.java.repositories.interfaces.I_GradoRepository;

public class GradoDAO implements I_GradoRepository{
    private final DataSource dataSource;

    // Constantes que definen las consultas SQL que utilizan os métodos para interactuar con la BD
    private static final String SQL_CREATE = "INSERT INTO grados (nombre_grado, ciclo, turno, docente, activo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM grados WHERE id_grado = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM grados";
    private static final String SQL_UPDATE = "UPDATE grados SET nombre_grado=?, ciclo=?, turno=?, docente=?, activo=? WHERE id_grado = ?";
    private static final String SQL_DELETE = "DELETE FROM grados WHERE id_grado = ?";
    
    public GradoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Grado grado) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CREATE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, grado.getNombreGrado().toString()); // Convierto el ENUM "NombreGrado" a cadena para guardarlo en la BD
            ps.setString(2, grado.getCiclo().toString());       // Convierto el ENUM "Ciclo" a cadena para guardarlo en la BD
            ps.setString(3, grado.getTurno().toString());       // Convierto el ENUM "Turno" a cadena para guardarlo en la BD
            ps.setString(4, grado.getDocente());
            ps.setBoolean(5, grado.isActivo());

            ps.executeUpdate();

            try (var keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    grado.setIdGrado(keys.getInt(1)); // Asigna el ID generado al objeto grado
                }
            }
            
        }
    }

    @Override
    public Grado findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return mapRow(rs);
                } else {
                    return null; // No se encontró el grado con el ID especificado
                }
            }
        }
    }

    @Override
    public List<Grado> findAll() throws SQLException {
        List<Grado> grados = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                grados.add(mapRow(rs));
            }
            return grados;
        }
    }

    @Override
    public int update(Grado grado) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            
            ps.setString(1, grado.getNombreGrado().toString()); // Convierto el ENUM "NombreGrado" a cadena para guardarlo en la BD
            ps.setString(2, grado.getCiclo().toString());       // Convierto el ENUM "Ciclo" a cadena para guardarlo en la BD
            ps.setString(3, grado.getTurno().toString());       // Convierto el ENUM "Turno" a cadena para guardarlo en la BD
            ps.setString(4, grado.getDocente());
            ps.setBoolean(5, grado.isActivo());
            ps.setInt(6, grado.getIdGrado()); // Establezco el ID del grado a actualizar

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);  
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas;
        }
    }

    private Grado mapRow(ResultSet rs) throws SQLException {
        // Vuelco uno a uno los datos del ResulSet en variables individuales para poder realizar las conversiones necesarias
        // de los ENUMs y luego crear un objeto Grado con esos datos.
        int idGrado = rs.getInt("id_grado");
        NombreGrado nombreGrado = NombreGrado.valueOf(rs.getString("nombre_grado"));    // Convierto la cadena del ResulSet a ENUM "NombreGrado"
        Ciclo ciclo = Ciclo.valueOf(rs.getString("ciclo"));                             // Convierto la cadena del ResulSet a ENUM "Ciclo"
        Turno turno = Turno.valueOf(rs.getString("turno"));                             // Convierto la cadena del ResulSet a ENUM "Turno"  
        String docente = rs.getString("docente");
        boolean activo = rs.getBoolean("activo");
        
        Grado grado = new Grado(idGrado, nombreGrado, ciclo, turno, docente, activo);
        return grado;
    }
}