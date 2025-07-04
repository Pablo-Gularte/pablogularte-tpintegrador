package ar.org.curso.centro8.java.models.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import ar.org.curso.centro8.java.models.entities.Asignatura;

public interface I_AsignaturaRepository {
    void create(Asignatura asignatura) throws SQLException;
    Asignatura findById(int id) throws SQLException;
    List<Asignatura> findAll() throws SQLException;
    int udpate(Asignatura asignatura) throws SQLException;
    int delete(int id) throws SQLException;
    List<Asignatura> findByDocente(String name) throws SQLException;
}
