package ar.org.curso.centro8.java.tests;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Asistencia;
import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.entities.Grado;
import ar.org.curso.centro8.java.entities.Nota;
import ar.org.curso.centro8.java.enums.Bimestre;
import ar.org.curso.centro8.java.repositories.AsignaturaRepository;
import ar.org.curso.centro8.java.repositories.AsistenciaRepository;
import ar.org.curso.centro8.java.repositories.EstudianteRepository;
import ar.org.curso.centro8.java.repositories.GradoRepository;
import ar.org.curso.centro8.java.repositories.NotaRepository;

public class TestEntidades {
    public static void main(String[] args) {
        // Creo la conexión a la base de datos
        HikariConfig config = ConfiguracionBD.getConfiguracion();

        try (HikariDataSource ds = new HikariDataSource(config)) {
            // -------------------
            // INICIAN LAS PRUEBAS
            // -------------------
            EstudianteRepository estudianteRepository = new EstudianteRepository(ds);
            GradoRepository gradoRepository = new GradoRepository(ds);
            String nombreGrado = "Cuarto";
            Grado grado = gradoRepository.findByNombreGrado(nombreGrado);
            
            System.out.println("***********************");
            System.out.println("[ INICIAN LAS PRUEBAS ]");
            System.out.println("***********************");
            System.out.println("Grado: " + grado.getNombreGrado() + " (docente a cargo: " + grado.getDocente() + ")");
            System.out.println();
            
            System.out.println("Listado de estudiantes del grado");
            for (Estudiante est : estudianteRepository.findByGrado(grado.getIdGrado())) {
                System.out.println("--> " + est.getNombre() + " " + est.getApellido() + " (id: " + est.getIdEstudiante() + ")");
            }
            System.out.println();
            
            int idestudiante = 159;
            Estudiante estudiante = estudianteRepository.findById(idestudiante);
            int anio = 2024;
            Bimestre bimestre = Bimestre.PRIMERO;
            System.out.println("Notas correspondientes a " + estudiante.getNombre() + " " + estudiante.getApellido());
            System.out.println("Año : " + anio);
            System.out.println("Bimestre: " + bimestre);
            NotaRepository notaRepository = new NotaRepository(ds);
            AsignaturaRepository asignaturaRepository = new AsignaturaRepository(ds);
            for (Nota nota : notaRepository.findByEstudiante(idestudiante)) {
                if (nota.getAnio() == anio && nota.getBimestre() == bimestre) {
                    System.out.println("--> " + asignaturaRepository.findById(nota.getIdAsignatura()).getNombreAsignatura() + ": " + nota.getNota());
                }
            }
            System.out.println();

            System.out.println("Listado de asistencias de " + estudiante.getNombre() + " " + estudiante.getApellido());
            System.out.println("Meses de marzo y abril de " + anio);
            int presentes = 0;
            int ausentes = 0;
            int tardes = 0;
            System.out.println("Fecha\t\t\tEstado");
            AsistenciaRepository asistenciaRepository = new AsistenciaRepository(ds);
            for (Asistencia asistencia : asistenciaRepository.findByEstudiante(idestudiante)) {
                if (asistencia.getFecha().toString().startsWith("2024-03") || asistencia.getFecha().toString().startsWith("2024-04")) {
                    System.out.println(asistencia.getFecha() + "\t" + asistencia.getTipoAsistencia());
                    switch (asistencia.getTipoAsistencia()) {
                        case PRESENTE:
                            presentes++;
                            break;
                        case AUSENTE:
                            ausentes++;
                            break;
                        case LLEGADA_TARDE:
                            tardes++;
                            break;
                    }
                }
            }
            System.out.println();
            System.out.println("Resumen de asistencias durante marzo y abril:");
            System.out.println("Presentes: " + presentes);
            System.out.println("Ausentes: " + ausentes);
            System.out.println("Llegadas tarde: " + tardes);
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}
