package ar.org.curso.centro8.java.entities;

import ar.org.curso.centro8.java.enums.Bimestre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Nota {
    private int idNota;
    private int nota;
    private int anio;
    private Bimestre bimestre;
    private int idEstudiante;
    private int idAsignatura;
}
