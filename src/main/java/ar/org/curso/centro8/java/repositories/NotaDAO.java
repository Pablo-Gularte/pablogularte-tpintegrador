package ar.org.curso.centro8.java.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ar.org.curso.centro8.java.entities.Nota;
import ar.org.curso.centro8.java.enums.Bimestre;
import ar.org.curso.centro8.java.repositories.interfaces.I_NotaRepository;

public class NotaDAO implements I_NotaRepository{
    private final DataSource dataSource;

    // Constantes que definen las consultas SQL que utilizan los métodos para interactuar con la BD
    private static final String SQL_CREATE = "INSERT INTO notas (id_nota, nota, anio, bimestre, id_estudiante, id_asignatura) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM notas WHERE id_nota = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM notas";
    private static final String SQL_UPDATE = "UPDATE notas SET nota=?, anio=?, bimestre=?, id_estudiante=?, id_asignatura=? WHERE id_nota = ?";
    private static final String SQL_DELETE = "DELETE FROM notas WHERE id_nota = ?";
    private static final String SQL_FIND_BY_ESTUDIANTE = "SELECT * FROM notas WHERE id_estudiante = ?";
    
    
    public NotaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Nota nota) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CREATE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, nota.getIdNota());
            ps.setInt(2, nota.getNota());
            ps.setInt(3, nota.getAnio());
            ps.setString(4, nota.getBimestre().toString()); // Convierto el ENUM "Bimestre" a cadena para guardarlo en la BD
            ps.setInt(5, nota.getIdEstudiante());
            ps.setInt(6, nota.getIdAsignatura());

            ps.executeUpdate();
            try (var keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    nota.setIdNota(keys.getInt(1));
                }
            }
        }
    }

    @Override
    public Nota findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs); // Retornar la nota encontrada
                }
                return null; // Si no se encuentra la nota, retornar null
            }
        }
    }

    @Override
    public List<Nota> findAll() throws SQLException {
        List<Nota> notas = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                notas.add(mapRow(rs));
            }
            return notas;
        }
    }

    @Override
    public int update(Nota nota) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, nota.getNota());
            ps.setInt(2, nota.getAnio());
            ps.setString(3, nota.getBimestre().toString()); // Convierto el ENUM "Bimestre" a cadena para guardarlo en la BD
            ps.setInt(4, nota.getIdEstudiante());
            ps.setInt(5, nota.getIdAsignatura());
            ps.setInt(6, nota.getIdNota());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas; // Retorna el número de filas afectadas
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
    public List<Nota> findByEstudiante(int idEstudiante) throws SQLException {
        List<Nota> notas = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ESTUDIANTE)) {
            ps.setInt(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notas.add(mapRow(rs));
                }
            }
        }
        return notas;
    }

    private Nota mapRow(ResultSet rs) throws SQLException {
        Nota nota = new Nota(
            rs.getInt("id_nota"),
            rs.getInt("nota"),
            rs.getInt("anio"),
            Bimestre.valueOf(rs.getString("bimestre")), // Convierto la cadena del Bimestre a su ENUM correspondiente
            rs.getInt("id_estudiante"),
            rs.getInt("id_asignatura")
        );
        return nota;
    }
}
