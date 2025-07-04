package ar.org.curso.centro8.java.models.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import ar.org.curso.centro8.java.models.entities.Asistencia;

public interface I_AsistenciaRepository {
    void create(Asistencia asistencia) throws SQLException;
    Asistencia findById(int id) throws SQLException;
    List<Asistencia> findAll() throws SQLException;
    int update(Asistencia asistencia) throws SQLException;
    int delete(int id) throws SQLException;
    List<Asistencia> findByEstudiante(int idEstudiante) throws SQLException;
}
