package ar.org.curso.centro8.java.entities;


import java.time.LocalDate;

import ar.org.curso.centro8.java.enums.TipoAsistencia;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Asistencia {
    private int idAsistencia;
    private LocalDate fecha;
    private int idEstudiante;
    private TipoAsistencia tipoAsistencia;
}
