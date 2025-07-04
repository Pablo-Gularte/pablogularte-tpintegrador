package ar.org.curso.centro8.java.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.org.curso.centro8.java.models.entities.Estudiante;
import ar.org.curso.centro8.java.models.entities.Grado;
import ar.org.curso.centro8.java.services.EstudianteService;
import ar.org.curso.centro8.java.services.GradoService;

@Controller
public class EstudianteController {
    private final EstudianteService estudianteService;
    private final GradoService gradoService;

    public EstudianteController(EstudianteService estudianteService, GradoService gradoService) {
        this.estudianteService = estudianteService;
        this.gradoService = gradoService;
    }

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Estudiante> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
            model.addAttribute("estudiantes", estudiantes);

            List<Grado> grados = gradoService.obtenerTodosLosGrados();
            model.addAttribute("grados", grados);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "** ERROR SQL ** al recuperar datos de estudiantes y cursos" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "** ERROR INESPERADO ** al recuperar datos de estudiantes y cursos" + e.getMessage());
        }

        return "index";
    }

    @GetMapping("/estudiante/alta")
    public String crearEstudiante(Model model) {
        return "estudiante-alta";
    }

    @GetMapping("/estudiante/modificar")
    public String modificarEstudiante(Model model) {
        return "estudiante-modifica";
    }

    @GetMapping("/estudiante/eliminar")
    public String eliminarEstudiante(Model model) {
        return "estudiante-eliminar";
    }

    // Filtros y búsquedas de datos
    @GetMapping("/estudiantes/FiltrarPorGrado")
    public String filtrarEstudiantesPorGrado(@RequestParam(value = "idGrado", required = false) Integer idGrado, Model model) {
        try {
            List<Estudiante> estudiantes;
            if (idGrado != null && idGrado != 0) {
                estudiantes = estudianteService.obtenerEstudiantesPorGrado(idGrado);
            } else {
                estudiantes = estudianteService.obtenerTodosLosEstudiantes();
            }
            model.addAttribute("estudiantes", estudiantes);

            List<Grado> grados = gradoService.obtenerTodosLosGrados();
            model.addAttribute("grados", grados);

            model.addAttribute("selectedGradoId", idGrado);

        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("error", "** ERROR ** al intentar filtrar estudiantes por grado: " + e.getMessage());
        }
        return "index";
    }
}
