package ar.org.curso.centro8.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Asignatura {
    private int idAsignatura;
    private String nombreAsignatura;
    private String docente;
}
