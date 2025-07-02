package ar.org.curso.centro8.java.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import ar.org.curso.centro8.java.entities.Grado;
import ar.org.curso.centro8.java.enums.Ciclo;
import ar.org.curso.centro8.java.enums.NombreGrado;
import ar.org.curso.centro8.java.enums.Turno;
import ar.org.curso.centro8.java.repositories.interfaces.I_GradoRepository;

@Repository
public class GradoRepository implements I_GradoRepository {
    private final DataSource dataSource;

    // Constantes que definen las consultas SQL que utilizan os métodos para
    // interactuar con la BD
    private static final String SQL_CREATE = "INSERT INTO grados (nombre_grado, ciclo, turno, docente, activo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM grados WHERE id_grado = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM grados";
    private static final String SQL_UPDATE = "UPDATE grados SET nombre_grado=?, ciclo=?, turno=?, docente=?, activo=? WHERE id_grado = ?";
    private static final String SQL_DELETE = "DELETE FROM grados WHERE id_grado = ?";
    private static final String SQL_FIND_BY_NOMBRE_GRADO = "SELECT * FROM grados WHERE nombre_grado = ? AND turno = ?";

    public GradoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Grado grado) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, grado.getNombreGrado().getDbValue());
            ps.setString(2, grado.getCiclo().getDbValue());
            ps.setString(3, grado.getTurno().getDbValue());
            ps.setString(4, grado.getDocente());
            ps.setBoolean(5, grado.isActivo());

            ps.executeUpdate();

            try (var keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    grado.setIdGrado(keys.getInt(1));
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
                if (rs.next()) {
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

            ps.setString(1, grado.getNombreGrado().getDbValue());
            ps.setString(2, grado.getCiclo().getDbValue());
            ps.setString(3, grado.getTurno().getDbValue());
            ps.setString(4, grado.getDocente());
            ps.setBoolean(5, grado.isActivo());
            ps.setInt(6, grado.getIdGrado());

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
    public Grado findByNombreYTurno(String nombre, String turno) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NOMBRE_GRADO)) {
            ps.setString(1, nombre);
            ps.setString(2, turno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                } else {
                    return null; // No se encontró el grado con el nombre especificado
                }
            }
            
        }
    }

    /**
     * Convierte un ResultSet en un objeto Grado.
     * Esta función es utilizada por los métodos findById y findAll
     * para mapear los resultados de una consulta SQL a un objeto Grado.
     * 
     * @param rs Este es el ResultSet que contiene los datos de la consulta SQL.
     *           Cada fila del ResultSet representa un grado.
     * @return Un objeto Grado que representa la fila actual del ResultSet.
     *         Si el ResultSet está vacío, se retornará null. Si se detecta un
     *         error al acceder a los datos del ResultSet, se lanzará una SQLException.
     * @throws SQLException Si ocurre un error al acceder a los datos del ResultSet.
     */
    private Grado mapRow(ResultSet rs) throws SQLException {
        Grado grado = new Grado(
            rs.getInt("id_grado"), 
            NombreGrado.fromDb(rs.getString("nombre_grado")),
            Ciclo.fromDb(rs.getString("ciclo")), 
            Turno.fromDb(rs.getString("turno")), 
            rs.getString("docente"), 
            rs.getBoolean("activo")
        );
        return grado;
    }
}