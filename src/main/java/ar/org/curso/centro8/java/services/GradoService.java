package ar.org.curso.centro8.java.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.org.curso.centro8.java.models.entities.Grado;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_GradoRepository;

@Service
public class GradoService {
    private I_GradoRepository gradoRepository;

    public GradoService(I_GradoRepository gradoRepository) {
        this.gradoRepository = gradoRepository;
    }

    public Grado guardarGrado(Grado grado) throws SQLException {
        if (grado.getIdGrado() == 0) gradoRepository.create(grado);
        else gradoRepository.update(grado);
        return grado;
    }

    public int eliminarGrado(int id) throws SQLException {
        return gradoRepository.delete(id);
    }

    public Grado buscarGradoPorId(int id) throws SQLException {
        return gradoRepository.findById(id);
    }

    public Grado buscarGradoPorNombreYTurno(String nombre, String turno) throws SQLException {
        return gradoRepository.findByNombreYTurno(nombre, turno);
    }

    public List<Grado> obtenerGradosPorNombre(String nombre) throws SQLException {
        return gradoRepository.findByNombre(nombre);
    }

    public List<Grado> obtenerGradosPorTurno(String turno) throws SQLException {
        return gradoRepository.findByTurno(turno);
    }

    public List<Grado> obtenerTodosLosGrados() throws SQLException {
        return gradoRepository.findAll();
    }
}
