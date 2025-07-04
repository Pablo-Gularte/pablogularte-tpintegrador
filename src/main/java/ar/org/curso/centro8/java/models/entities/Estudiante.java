package ar.org.curso.centro8.java.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Estudiante {
    private int idEstudiante;
    private int idGrado;
    private String nombre;
    private String apellido;
    private int edad;
    private String direccion;
    private String nombreMadre;
    private String nombrePadre;
    private boolean hermanoEnEscuela;
    private boolean activo;
    private String nombreGrado;
    private String turnoGrado;
}
