package ar.org.curso.centro8.java.entities;

import ar.org.curso.centro8.java.enums.Bimestre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Nota {
    private int idNota;
    private int nota;
    private int anio;
    private Bimestre bimestre;
    private int idEstudiante;
    private int idAsignatura;
}
