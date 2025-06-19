package ar.org.curso.centro8.java.entities;

import ar.org.curso.centro8.java.enums.Ciclo;
import ar.org.curso.centro8.java.enums.NombreGrado;
import ar.org.curso.centro8.java.enums.Turno;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Grado {
    private int idGrado;
    private NombreGrado nombreGrado;
    private Ciclo ciclo;
    private Turno turno;
    private String docente;
    private boolean activo;
}
