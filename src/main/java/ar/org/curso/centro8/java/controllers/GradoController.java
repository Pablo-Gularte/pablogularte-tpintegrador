package ar.org.curso.centro8.java.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GradoController {

    @GetMapping("/grados")
    public String home(Model model) {
        String texto = "Texto de prueba";
        model.addAttribute("texto", texto);
        return "cursos-listar";
    }
}
