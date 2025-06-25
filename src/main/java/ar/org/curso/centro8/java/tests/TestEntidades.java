package ar.org.curso.centro8.java.tests;

import java.sql.SQLException;
import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.entities.Asistencia;
import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.entities.Grado;
import ar.org.curso.centro8.java.entities.Nota;
import ar.org.curso.centro8.java.enums.Bimestre;
import ar.org.curso.centro8.java.enums.TipoAsistencia;
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
            // Defino los valores de las variables para las pruebas
            String nombreGrado = "Quinto";
            int anio = 2025;
            Bimestre bimestre = Bimestre.PRIMERO;
            String periodoAsistencia = "2025-03";
            GradoRepository gradoRepository = new GradoRepository(ds);
            EstudianteRepository estudianteRepository = new EstudianteRepository(ds);

            // Muestro cartel de inicio de pruebas en pantalla
            System.out.println(" *********************");
            System.out.println("[ INICIAN LAS PRUEBAS ]");
            System.out.println(" *********************");
            System.out.println();

            // Muestro los datos del grado indicado.
            Grado grado = gradoRepository.findByNombreGrado(nombreGrado);
            List<Estudiante> estudiantes = estudianteRepository.findByGrado(grado.getIdGrado());
            System.out.println("[ Datos del grado " + grado.getNombreGrado() + " ]");
            System.out.println("Docente a cargo: " + grado.getDocente());
            System.out.println("Total de estudiantes en el grado: " + estudiantes.size() + "\n");


            // Muestro las notas de los estudiantes del grado indicado.
            System.out.println("[ Notas de los estudiantes del grado para el año " + anio + " y bimestre " + bimestre + " ]");
            NotaRepository notaRepository = new NotaRepository(ds);
            AsignaturaRepository asignaturaRepository = new AsignaturaRepository(ds);
            estudiantes
                .stream()
                .forEach(estudiante -> {
                System.out.println("=> " + estudiante.getNombre() + " " + estudiante.getApellido());
                try {
                    List<Nota> notas = notaRepository.findByEstudiante(estudiante.getIdEstudiante());
                    notas.stream()
                        .filter(n -> n.getAnio() == anio && n.getBimestre() == bimestre)
                        .forEach(n -> {
                            try {
                                String nombreAsignatura = asignaturaRepository.findById(n.getIdAsignatura()).getNombreAsignatura();
                                int nota = n.getNota();
                                System.out.println("   -> " + nombreAsignatura + ", Nota: " + nota);
                            } catch (Exception SQLException) {
                                System.out.println("Error al obtener el nombre de la asignatura para la nota: " + n.getIdAsignatura());
                            }
                        });
                        System.out.println();
                } catch (SQLException e) {
                    System.out.println("Error al obtener las notas del estudiante: " + estudiante.getNombre() + " " + estudiante.getApellido() + " - " + e.getMessage());
                }
            });
            System.out.println();

            // Muestro las asistencias de los estudiantes para el año y período indicados.
            System.out.println("[ Asistencias de los estudiantes del grado para el período " + periodoAsistencia + " ]");
            AsistenciaRepository asistenciaRepository = new AsistenciaRepository(ds);
            estudiantes.stream()
                .forEach(estudiante -> {
                    System.out.println("=> " + estudiante.getNombre() + " " + estudiante.getApellido());
                    try {
                        List<Asistencia> asistencias = asistenciaRepository.findByEstudiante(estudiante.getIdEstudiante());
                        int presentes = 0;
                        int ausentes = 0;
                        int tardes = 0;
                        for (Asistencia a : asistencias) {
                            if (a.getFecha().toString().startsWith(periodoAsistencia)) {
                                if (a.getTipoAsistencia() == TipoAsistencia.PRESENTE) presentes++;
                                if (a.getTipoAsistencia() == TipoAsistencia.AUSENTE) ausentes++;
                                if (a.getTipoAsistencia() == TipoAsistencia.LLEGADA_TARDE) tardes++;
                                System.out.println("   -> " + a.getFecha() + ": " + a.getTipoAsistencia());
                            }
                        }
                        System.out.println("Total de presnetes: " + presentes);
                        System.out.println("Total de ausentes: " + ausentes);
                        System.out.println("Total de llegadas tarde: " + tardes);
                        System.out.println();
                    } catch (SQLException e) {
                        System.out.println("Error al recuperar las asistencias del estudiante de id: " + estudiante.getIdEstudiante() + " " + e.getMessage());
                    }
                });
            System.out.println();

            // Muestor cartel de finalización de pruebas en pantalla
            System.out.println();
            System.out.println(" *********************");
            System.out.println("[ FINALIZAN LAS PRUEBAS ]");
            System.out.println(" *********************");

        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos: " + e.getMessage());
        }
    }
}
