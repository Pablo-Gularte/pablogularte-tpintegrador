package ar.org.curso.centro8.java.models.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import ar.org.curso.centro8.java.models.entities.Estudiante;

public interface I_EstudianteRepository {
    void create(Estudiante estudiante) throws SQLException;
    Estudiante findById(int id) throws SQLException;
    List<Estudiante> findAll() throws SQLException;
    int update(Estudiante estudiante) throws SQLException;
    int delete(int id) throws SQLException;
    List<Estudiante> findByGrado(int idGrado) throws SQLException;
}
