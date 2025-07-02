package ar.org.curso.centro8.java.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import ar.org.curso.centro8.java.entities.Grado;

public interface I_GradoRepository {
    void create(Grado grado) throws SQLException;
    Grado findById(int id) throws SQLException;
    List<Grado> findAll() throws SQLException;
    int update(Grado grado) throws SQLException;
    int delete(int id) throws SQLException;
    Grado findByNombreYTurno(String nombre, String turno) throws SQLException;
}
