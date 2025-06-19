package ar.org.curso.centro8.java.entities;

import java.util.Date;

import ar.org.curso.centro8.java.enums.TipoAsistencia;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Asistencia {
    private int idAsistencia;
    private Date fecha;
    private int idEstudiante;
    private TipoAsistencia tipoAsistencia;
}
