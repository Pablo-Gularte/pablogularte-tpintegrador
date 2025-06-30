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

import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.repositories.interfaces.I_EstudianteRepository;

@Repository
public class EstudianteRepository implements I_EstudianteRepository {

    private final DataSource dataSource;

    // Constantes que definen las consultas SQL que utilizan os métodos para interactuar con la BD
    private static final String SQL_CREATE = "INSERT INTO estudiantes (nombre, apellido, edad, id_grado, direccion, nombre_madre, nombre_padre, hermano_en_escuela, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM estudiantes WHERE id_estudiante = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM estudiantes";
    private static final String SQL_UPDATE = "UPDATE estudiantes SET nombre = ?, apellido = ?, edad = ?, id_grado = ?, direccion = ?, nombre_madre = ?, nombre_padre = ?, hermano_en_escuela = ?, activo = ? WHERE id_estudiante = ?";
    private static final String SQL_DELETE = "DELETE FROM estudiantes WHERE id_estudiante = ?";
    private static final String SQL_FIND_BY_GRADO = "SELECT * FROM estudiantes WHERE id_grado = ?";

    public EstudianteRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Estudiante estudiante) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setInt(3, estudiante.getEdad());
            ps.setInt(4, estudiante.getIdGrado());
            ps.setString(5, estudiante.getDireccion());
            ps.setString(6, estudiante.getNombreMadre());
            ps.setString(7, estudiante.getNombrePadre());
            ps.setBoolean(8, estudiante.isHermanoEnEscuela());
            ps.setBoolean(9, estudiante.isActivo());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    estudiante.setIdEstudiante(keys.getInt(1)); // Asigna el ID generado al objeto estudiante
                }

            }
        }
    }

    @Override
    public Estudiante findById(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                } else {
                    return null; // No se encontró el estudiante
                }
            }

        }
    }

    @Override
    public List<Estudiante> findAll() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                estudiantes.add(mapRow(rs));
            }
        }
        return estudiantes;
    }

    @Override
    public int update(Estudiante estudiante) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setInt(3, estudiante.getEdad());
            ps.setInt(4, estudiante.getIdGrado());
            ps.setString(5, estudiante.getDireccion());
            ps.setString(6, estudiante.getNombreMadre());
            ps.setString(7, estudiante.getNombrePadre());
            ps.setBoolean(8, estudiante.isHermanoEnEscuela());
            ps.setBoolean(9, estudiante.isActivo());
            ps.setInt(10, estudiante.getIdEstudiante());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas; // Retorna el número de filas afectadas por la actualización
            
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas; // Retorna el número de filas afectadas por la eliminación
        }
    }

    @Override
    public List<Estudiante> findByGrado(int idGrado) throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_GRADO)) {
            ps.setInt(1, idGrado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    estudiantes.add(mapRow(rs));
                }
            }
        }
        return estudiantes;
    }

    private Estudiante mapRow(ResultSet rs) throws SQLException {
        Estudiante estudiante = new Estudiante(
            rs.getInt("id_estudiante"),
            rs.getInt("id_grado"),
            rs.getString("nombre"),
            rs.getString("apellido"),
            rs.getInt("edad"),
            rs.getString("direccion"),
            rs.getString("nombre_madre"),
            rs.getString("nombre_padre"),
            rs.getBoolean("hermano_en_escuela"),
            rs.getBoolean("activo")
        );

        return estudiante;
    }
}
