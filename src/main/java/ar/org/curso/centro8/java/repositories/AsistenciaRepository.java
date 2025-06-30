package ar.org.curso.centro8.java.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import ar.org.curso.centro8.java.entities.Asistencia;
import ar.org.curso.centro8.java.enums.TipoAsistencia;
import ar.org.curso.centro8.java.repositories.interfaces.I_AsistenciaRepository;

@Repository
public class AsistenciaRepository implements I_AsistenciaRepository {
    private final DataSource dataSource;
    
    // Constantes que definen las consultas SQL que utilizan los métodos para interactuar con la BD
    private static final String SQL_CREATE = "INSERT INTO asistencias (fecha, id_estudiante, tipo_asistencia) VALUES (?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM asistencias WHERE id_asistencia = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM asistencias";
    private static final String SQL_UPDATE = "UPDATE asistencias SET fecha=?, id_estudiante=?, tipo_asistencia=? WHERE id_asistencia = ?";
    private static final String SQL_DELETE = "DELETE FROM asistencias WHERE id_asistencia = ?";
    private static final String SQL_FIND_BY_ESTUDIANTE = "SELECT * FROM asistencias WHERE id_estudiante = ?";

    public AsistenciaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Asistencia asistencia) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, java.sql.Date.valueOf(asistencia.getFecha())); // Convierto el formato de fecha Date de Java al formato de fecha SQL
            ps.setInt(2, asistencia.getIdEstudiante());
            ps.setString(3, asistencia.getTipoAsistencia().getDbValue());

            ps.executeUpdate();

            try (var keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    asistencia.setIdAsistencia(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Asistencia findById(int id) throws SQLException {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null; // Retorna null si no se encuentra la asistencia
    }

    @Override
    public List<Asistencia> findAll() throws SQLException {
        List<Asistencia> asistencias = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                asistencias.add(mapRow(rs));
            }
            return asistencias;
        }
    }

    @Override
    public int update(Asistencia asistencia) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setDate(1, java.sql.Date.valueOf(asistencia.getFecha()));
            ps.setInt(2, asistencia.getIdEstudiante());
            ps.setString(3, asistencia.getTipoAsistencia().getDbValue());
            ps.setInt(4, asistencia.getIdAsistencia());

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
    public List<Asistencia> findByEstudiante(int idEstudiante) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ESTUDIANTE)) {
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            List<Asistencia> asistencias = new ArrayList<>();
            while (rs.next()) {
                asistencias.add(mapRow(rs));
            }
            return asistencias;
        }
    }

    /**
     * Convierte una fila del ResultSet a un objeto Asistencia.
     * Esta función es utilizada para mapear los resultados de una consulta SQL
     * a un objeto Asistencia.
     * 
     * @param rs El ResultSet que contiene los datos de la asistencia.
     *           Se espera que contenga las columnas: id_asistencia, fecha, id_estudiante, tipo_asistencia.
     * 
     * @return Un objeto Asistencia que representa la fila del ResultSet.
     *         Si el ResultSet no contiene los datos esperados, puede lanzar una SQLException.
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet.
     */
    private Asistencia mapRow(ResultSet rs) throws SQLException {
        // Convierto el String de la BD al ENUM TipoAsistencia correspondiente
        Asistencia asistencia = new Asistencia(
            rs.getInt("id_asistencia"),
            rs.getDate("fecha").toLocalDate(),
            rs.getInt("id_estudiante"),
            TipoAsistencia.fromDb(rs.getString("tipo_asistencia"))
        );
        return asistencia;
    }
}
