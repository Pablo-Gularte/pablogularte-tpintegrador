package ar.org.curso.centro8.java.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import ar.org.curso.centro8.java.entities.Asignatura;
import ar.org.curso.centro8.java.repositories.interfaces.I_AsignaturaRepository;

@Repository
public class AsignaturaRepository implements I_AsignaturaRepository {
    private final DataSource dataSource;

    // Constantes que definen las consultas SQL que utilizan los métodos para interactuar con la BD
    private static final String SQL_CREATE = "INSERT INTO asignaturas (nombre_asignatura, docente) VALUES (?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM asignaturas WHERE id_asignatura = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM asignaturas";
    private static final String SQL_UPDATE = "UPDATE asignaturas SET nombre_asignatura=?, docente=? WHERE id_asignatura = ?";
    private static final String SQL_DELETE = "DELETE FROM asignaturas WHERE id_asignatura = ?";
    private static final String SQL_FIND_BY_DOCENTE = "SELECT * FROM asignaturas WHERE docente = ?";
    
    public AsignaturaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Asignatura asignatura) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, asignatura.getIdAsignatura());
            ps.setString(2, asignatura.getNombreAsignatura());
            ps.setString(3, asignatura.getDocente());

            ps.executeUpdate();
            
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    asignatura.setIdAsignatura(keys.getInt(1)); // Asigna el ID generado al objeto asignatura
                }
            }
        }
    }

    @Override
    public Asignatura findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                } else {
                    return null; // No se encontró la asignatura
                }
            }
        }
    }

    @Override
    public List<Asignatura> findAll() throws SQLException {
        List<Asignatura> asignaturas = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                asignaturas.add(mapRow(rs));
            }
        }
        return asignaturas;
    }

    @Override
    public int udpate(Asignatura asignatura) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, asignatura.getNombreAsignatura());
            ps.setString(2, asignatura.getDocente());
            ps.setInt(3, asignatura.getIdAsignatura());

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

    @Override
    public List<Asignatura> findByDocente(String name) throws SQLException {
        List<Asignatura> asignaturas = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_DOCENTE)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    asignaturas.add(mapRow(rs));
                }
            }
        }
        return asignaturas;
    }

    private Asignatura mapRow(ResultSet rs) throws SQLException {
        Asignatura asignatura = new Asignatura(
            rs.getInt("id_asignatura"),
            rs.getString("nombre_asignatura"),
            rs.getString("docente")
        );
        return asignatura;
    }
}
