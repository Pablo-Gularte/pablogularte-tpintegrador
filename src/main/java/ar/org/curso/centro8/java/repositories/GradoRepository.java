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
    private static final String SQL_FIND_BY_NOMBRE_GRADO = "SELECT * FROM grados WHERE nombre_grado = ?";

    public GradoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Grado grado) throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(SQL_CREATE, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, convertirValorEnumHaciaBD(grado.getNombreGrado())); // Convierto el ENUM "NombreGrado" a
                                                                                // cadena para guardarlo en la BD
            ps.setString(2, convertirValorEnumHaciaBD(grado.getCiclo())); // Convierto el ENUM "Ciclo" a cadena para
                                                                          // guardarlo en la BD
            ps.setString(3, convertirValorEnumHaciaBD(grado.getTurno())); // Convierto el ENUM "Turno" a cadena para
                                                                          // guardarlo en la BD
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

            ps.setString(1, convertirValorEnumHaciaBD(grado.getNombreGrado())); // Convierto el ENUM "NombreGrado" a
                                                                                // cadena para guardarlo en la BD
            ps.setString(2, convertirValorEnumHaciaBD(grado.getCiclo())); // Convierto el ENUM "Ciclo" a cadena para
                                                                          // guardarlo en la BD
            ps.setString(3, convertirValorEnumHaciaBD(grado.getTurno())); // Convierto el ENUM "Turno" a cadena para
                                                                          // guardarlo en la BD
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
    public Grado findByNombreGrado(String nombre) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_NOMBRE_GRADO)) {
            ps.setString(1, nombre);
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
        // Vuelco uno a uno los datos del ResulSet en variables individuales para poder
        // realizar las conversiones necesarias
        // de los ENUMs y luego crear un objeto Grado con esos datos.
        int idGrado = rs.getInt("id_grado");
        String docente = rs.getString("docente");
        boolean activo = rs.getBoolean("activo");

        // Convierto el String de BD del campo "nombre_grado" a su ENUM NombreGrado
        // correspondiente
        NombreGrado nombreGrado = convertirEnumNombreGradoDesdeBD(rs.getString("nombre_grado"));

        // Convierto el String de BD del campo "ciclo" a su ENUM Ciclo correspondiente
        Ciclo ciclo = convertirEnumCicloDesdeBD(rs.getString("ciclo"));

        // Convierto el String de BD del campo "turno" a su ENUM Turno correspondiente
        Turno turno = convertirEnumTurnoDesdeBD(rs.getString("turno"));

        Grado grado = new Grado(idGrado, nombreGrado, ciclo, turno, docente, activo);
        return grado;
    }

    /**
     * Convierte un valor del ENUM NombreGrado a su representación en la base de datos.
     * @param nombreGrado Es el valor del ENUM NombreGrado que se desea convertir.
     *                    Puede ser PRIMERO, SEGUNDO, TERCERO, CUARTO, QUINTO,
     *                    SEXTO o SEPTIMO.
     * @return Una cadena que representa el valor del ENUM NombreGrado en la base de datos.
     *         Retorna null si el valor del ENUM no es reconocido.
     */
    private String convertirValorEnumHaciaBD(NombreGrado nombreGrado) {
        return switch (nombreGrado) {
            case PRIMERO -> "Primero";
            case SEGUNDO -> "Segundo";
            case TERCERO -> "Tercero";
            case CUARTO -> "Cuarto";
            case QUINTO -> "Quinto";
            case SEXTO -> "Sexto";
            case SEPTIMO -> "Séptimo";
            default -> null;
        };
    }

    /**
     * Convierte un valor del ENUM Ciclo a su representación en la base de datos.
     * 
     * @param ciclo Es el valor del ENUM Ciclo que se desea convertir.
     *              Puede ser PRIMERO o SEGUNDO.
     * @return Una cadena que representa el valor del ENUM Ciclo en la base de datos.
     *         Retorna null si el valor del ENUM no es reconocido.
     */
    private String convertirValorEnumHaciaBD(Ciclo ciclo) {
        return switch (ciclo) {
            case PRIMERO -> "Primer ciclo";
            case SEGUNDO -> "Segundo ciclo";
            default -> null;
        };
    }

    /**
     * Convierte un valor del ENUM Turno a su representación en la base de datos.
     * 
     * @param turno Es el valor del ENUM Turno que se desea convertir.
     *              Puede ser MAÑANA, TARDE o JORNADA_COMPLETA.
     * @return Una cadena que representa el valor del ENUM Turno en la base de datos.
     *         Retorna null si el valor del ENUM no es reconocido.
     */
    private String convertirValorEnumHaciaBD(Turno turno) {
        return switch (turno) {
            case MAÑANA -> "Mañana";
            case TARDE -> "Tarde";
            case JORNADA_COMPLETA -> "Jornada completa";
            default -> null;
        };
    }

    /**
     * Convierte un valor de cadena de la base de datos a su correspondiente ENUM NombreGrado.
     * 
     * @param valorEnum Es la cadena que representa el valor del ENUM NombreGrado
     *                  en la base de datos. Puede ser "Primero", "Segundo", "Tercero",
     *                  "Cuarto", "Quinto", "Sexto" o "Séptimo".
     *                  Si el valor no es reconocido, se retornará null.
     * @return El valor del ENUM NombreGrado correspondiente a la cadena
     *         proporcionada. Retorna null si la cadena no es reconocida.
     */
    private NombreGrado convertirEnumNombreGradoDesdeBD(String valorEnum) {
        return switch (valorEnum) {
            case "Primero" -> NombreGrado.PRIMERO;
            case "Segundo" -> NombreGrado.SEGUNDO;
            case "Tercero" -> NombreGrado.TERCERO;
            case "Cuarto" -> NombreGrado.CUARTO;
            case "Quinto" -> NombreGrado.QUINTO;
            case "Sexto" -> NombreGrado.SEXTO;
            case "Séptimo" -> NombreGrado.SEPTIMO;
            default -> null;
        };
    }

    /**
     * Convierte un valor de cadena de la base de datos a su correspondiente ENUM Ciclo.
     * 
     * @param valorEnum Es la cadena que representa el valor del ENUM Ciclo
     *                  en la base de datos. Puede ser "Primer ciclo" o "Segundo ciclo
     * @return El valor del ENUM Ciclo correspondiente a la cadena
     *         proporcionada. Retorna null si la cadena no es reconocida.
     */
    private Ciclo convertirEnumCicloDesdeBD(String valorEnum) {
        return switch (valorEnum) {
            case "Primer ciclo" -> Ciclo.PRIMERO;
            case "Segundo ciclo" -> Ciclo.SEGUNDO;
            default -> null;
        };
    }

    /**
     * Convierte un valor de cadena de la base de datos a su correspondiente ENUM Turno.
     * 
     * @param valorEnum Es la cadena que representa el valor del ENUM Turno
     *                  en la base de datos. Puede ser "Mañana", "Tarde" o "Jornada completa".
     *                  Si el valor no es reconocido, se retornará null.
     * @return El valor del ENUM Turno correspondiente a la cadena
     *         proporcionada. Retorna null si la cadena no es reconocida.
     */
    private Turno convertirEnumTurnoDesdeBD(String valorEnum) {
        return switch (valorEnum) {
            case "Mañana" -> Turno.MAÑANA;
            case "Tarde" -> Turno.TARDE;
            case "Jornada completa" -> Turno.JORNADA_COMPLETA;
            default -> null;
        };
    }
}