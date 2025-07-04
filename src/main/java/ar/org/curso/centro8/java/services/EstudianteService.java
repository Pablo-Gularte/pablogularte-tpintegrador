package ar.org.curso.centro8.java.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import ar.org.curso.centro8.java.models.entities.Estudiante;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_EstudianteRepository;

@Service
public class EstudianteService {
    private I_EstudianteRepository estudianteRepository;

    public EstudianteService(I_EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Estudiante guardarEstudiante(Estudiante estudiante) throws SQLException {
        // Creo estudiante nuevo
        if (estudiante.getIdEstudiante() == 0) estudianteRepository.create(estudiante);
        // Actualizo estudiante existente
        else estudianteRepository.update(estudiante);
        
        return estudiante;
    }

    public int eliminarEstudiante(int id) throws SQLException {
        return estudianteRepository.delete(id);
    }

    public Estudiante buscarEstudiantePorId(int id) throws SQLException {
        return estudianteRepository.findById(id);
    }

    public List<Estudiante> obtenerEstudiantesPorGrado(int id) throws SQLException {
        return estudianteRepository.findByGrado(id);
    }

    public List<Estudiante> obtenerTodosLosEstudiantes() throws SQLException {
        return estudianteRepository.findAll();
    }
}
