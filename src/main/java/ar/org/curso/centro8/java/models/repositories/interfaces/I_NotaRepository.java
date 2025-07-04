package ar.org.curso.centro8.java.models.repositories.interfaces;

import java.sql.SQLException;
import java.util.List;

import ar.org.curso.centro8.java.models.entities.Nota;

public interface I_NotaRepository {
    void create(Nota nota) throws SQLException;
    Nota findById(int id) throws SQLException;
    List<Nota> findAll() throws SQLException;
    int update(Nota nota) throws SQLException;
    int delete(int id) throws SQLException;
    List<Nota> findByEstudiante(int idEstudiante) throws SQLException;
}
