package ar.org.curso.centro8.java.tests;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ar.org.curso.centro8.java.models.entities.Asistencia;
import ar.org.curso.centro8.java.models.entities.Estudiante;
import ar.org.curso.centro8.java.models.entities.Nota;
import ar.org.curso.centro8.java.models.repositories.AsignaturaRepository;
import ar.org.curso.centro8.java.models.repositories.AsistenciaRepository;
import ar.org.curso.centro8.java.models.repositories.EstudianteRepository;
import ar.org.curso.centro8.java.models.repositories.GradoRepository;
import ar.org.curso.centro8.java.models.repositories.NotaRepository;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_AsignaturaRepository;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_AsistenciaRepository;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_EstudianteRepository;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_GradoRepository;
import ar.org.curso.centro8.java.models.repositories.interfaces.I_NotaRepository;

@SpringBootApplication(scanBasePackages = "ar.org.curso.centro8.java")
public class TestRepositories {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(TestRepositories.class, args)) {
            // Defino los valores de las variables para las pruebas
            String nombreGradoPrueba = "Tercero";
            String turnoGradoPrueba = "Mañana";
            String periodoAsistencia = "2024-10";
            String bimestrePrueba = "Segundo bimestre";
            int anioPrueba = 2024;

            // Muestro cartel de inicio de pruebas en pantalla
            mostrarCartelInicio();

            // Muestro los datos del grado indicado y recupero el listado de estudiantes.
            List<Estudiante> listadoEstudiantes = mostrarDatosDelGrado(nombreGradoPrueba, turnoGradoPrueba, context);

            // Muestro las notas de los estudiantes del grado indicado.
            mostrarNotasDeEstudiantes(listadoEstudiantes, anioPrueba, bimestrePrueba, context);

            // Muestro las asistencias de los estudiantes para el año y mes indicados.
            mostrarAsistenciasEstudiantes(listadoEstudiantes, periodoAsistencia, context);

            // Creo nuevo estudiante para el grado en prueba, lo agrego a la BD y lo utilizo para pruebas de modificación y eliminación
            Estudiante nuevoEstudiante = crearNuevoEstudiante(nombreGradoPrueba, turnoGradoPrueba, context);

            // Prueba de modificación de datos
            modificarEstudiante(nuevoEstudiante, context);

            // Prueba de eliminación de datos
            eliminarEstudiante(nuevoEstudiante, context);

            // Muestor cartel de finalización de pruebas en pantalla
            mostrarCartelFinal();
        } catch (Exception e) {
            System.out.println("*** ERROR GENERAL detectado durante la prueba de los repositorios con Spring Boot ***");
            e.printStackTrace();
        }
    }

    /**
     * Elimina el estudiante pasado por parámetro de la base de datos.
     * @param estudiante Es el estudiante que se debe eliminar
     * @param contextoApp Contiene las configuraciones del contexto de la aplicación que permite definir los accesos a la BD mediante repositorios.
     */
    private static void eliminarEstudiante(Estudiante estudiante, ConfigurableApplicationContext contextoApp) {
        I_EstudianteRepository estudianteRepo = contextoApp.getBean(EstudianteRepository.class);

        try {
            System.out.println("[ ELIMINO nuevo estudiante ]");
            int filasAfectadas = estudianteRepo.delete(estudiante.getIdEstudiante());
            if (filasAfectadas > 0) System.out.println("--> SE ELIMINÓ CORRECTAMENTE el estudiante de ID: " + estudiante.getIdEstudiante() + " de la BD.");
            else System.out.println("--> NO SE PUDO ELIMINAR el estudiante de la BD.");
            System.out.println();
        } catch (Exception e) {
            System.err.println("** ERROR ** detectado al intentar eliminar el estudiante de la BD.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Modifica el nombre y apellido del estudiante que se pasa por parámetro.
     * 
     * @param estudiante Es el estudiante al que se modificará nombre y apellido.
     * @param contextoApp Contiene las configuraciones del contexto de la aplicación que permite definir los accesos a la BD mediante repositorios.
     */
    private static void modificarEstudiante(Estudiante estudiante, ConfigurableApplicationContext contextoApp) {
        I_EstudianteRepository estudianteRepo = contextoApp.getBean(EstudianteRepository.class);

        try {
            System.out.println("[ MODIFICO nuevo estudiante ]");
            System.out.println("--> Modifico nombre y apellido de estudiante");
            System.out.println("----> " + estudianteRepo.findById(estudiante.getIdEstudiante()));
            estudiante.setNombre("Nombre cambiado");
            estudiante.setApellido("Apellido también cambiado");
            estudianteRepo.update(estudiante);
            System.out.println("----> " + estudianteRepo.findById(estudiante.getIdEstudiante()));
            System.out.println();    
        } catch (Exception e) {
            System.err.println("** ERROR ** detectado al intentar modificar el estudiante.");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Crea un objeto de tipo Estudiante y lo inserta en la BD. Si falla devuelve null.
     * @param grado Es el grado donde se creará el nuevo estudiante.
     * @param turno Es el turno del grado donde se creará el nuevo estudiante.
     * @param contextoApp Contiene las configuraciones del contexto de la aplicación que permite definir los accesos a la BD mediante repositorios.
     * @return El nuevo estudiante creado si no hubo fallas o null si hubo fallas.
     */
    private static Estudiante crearNuevoEstudiante(String grado, String turno, ConfigurableApplicationContext contextoApp) {
        I_GradoRepository gradoRepo = contextoApp.getBean(GradoRepository.class);
        I_EstudianteRepository estudianteRepo = contextoApp.getBean(EstudianteRepository.class);

        try {
            Estudiante nuevoEstudiante = new Estudiante(
                    0,
                    gradoRepo.findByNombreYTurno(grado, turno).getIdGrado(),
                    "Estudiante",
                    "Nuevo",
                    6,
                    "Dirección de estudiante nuevo",
                    "Nombre de la madre de estudiante nuevo",
                    "Nombre del padre de estudiante nuevo",
                    false,
                    true,
                    gradoRepo.findByNombreYTurno(grado, turno).getNombreGrado().getDbValue(),
                    gradoRepo.findByNombreYTurno(grado, turno).getTurno().getDbValue()
                    );

            System.out.println("[ CREO nuevo estudiante ]");
            estudianteRepo.create(nuevoEstudiante);
            if (nuevoEstudiante.getIdEstudiante() > 0) {
                System.out.println("--> Creo al estudiante de ID: " + nuevoEstudiante.getIdEstudiante());
                System.out.println(nuevoEstudiante);
                System.out.println("--> Recupero de la BD al estudiante de ID: " + nuevoEstudiante.getIdEstudiante() + " de la BD");
                System.out.println(estudianteRepo.findById(nuevoEstudiante.getIdEstudiante()));
            } else {
                System.out.println("--> No se encontró el nuevo estudiante en la BD.");
            }
            System.out.println();

            return nuevoEstudiante;
        } catch (Exception e) {
            System.err.println("** ERROR ** al intentar crear un nuevo estudiante para insertarlo en la base de datos");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Muestra por consola las asistencias del listado de estudiantes pasado por parámetro en el período especificado.
     * @param listadoEstudiantes Listado de estudiantes cuyas asistencias se mostrarán.
     * @param periodoAsistencia PEríodo a considerar para el listado de asistencias.
     * @param contextoApp Contiene las configuraciones del contexto de la aplicación que permite definir los accesos a la BD mediante repositorios.
     */
    private static void mostrarAsistenciasEstudiantes(List<Estudiante> listadoEstudiantes, String periodoAsistencia, ConfigurableApplicationContext contextoApp) {
        I_AsistenciaRepository asistenciaRepo = contextoApp.getBean(AsistenciaRepository.class);
        
        System.out.println("[ Asistencias de los estudiantes del grado durante el período " + periodoAsistencia + " ]");
        listadoEstudiantes.forEach(estudiante -> {
            List<Asistencia> asistenciasPorEstudiante = new ArrayList<>();
            try {
                asistenciasPorEstudiante = asistenciaRepo.findByEstudiante(estudiante.getIdEstudiante());
            } catch (Exception e) {
                System.err.println("** ERROR ** al intentar recuperar las asistencias de estudiante_id: "
                        + estudiante.getIdEstudiante());
                System.err.println(e.getMessage());
            }
            if (asistenciasPorEstudiante.size() > 0) {
                System.out.println("--> [ " + estudiante.getNombre() + " " + estudiante.getApellido() + " ]");
                int cuentoPresentes = 0;
                int cuentoAusentes = 0;
                int cuentoTardes = 0;
                for (Asistencia asistencia : asistenciasPorEstudiante) {
                    if (asistencia.getFecha().toString().startsWith(periodoAsistencia)) {
                        System.out.println(
                                "--> " + asistencia.getFecha() + ": " + asistencia.getTipoAsistencia().getDbValue());
                        if (asistencia.getTipoAsistencia().getDbValue().equals("Presente")) cuentoPresentes++;
                        if (asistencia.getTipoAsistencia().getDbValue().equals("Ausente")) cuentoAusentes++;
                        if (asistencia.getTipoAsistencia().getDbValue().equals("Llegada tarde")) cuentoTardes++;
                    }

                }
                System.out.println("Total presentes: " + cuentoPresentes);
                System.out.println("Total ausentes: " + cuentoAusentes);
                System.out.println("Total llegadas tarde: " + cuentoTardes);
                System.out.println();
            }
        });
        System.out.println();
    }

    /**
     * Muestra las notas del listado de estudiantes pasado por parámetro para el año y bimestre especificado.
     * 
     * @param listadoEstudiantes Contiene el listado de estudiantes a analizar.
     * @param anioPrueba Es el año que se debe considerar para mostrar las notas.
     * @param bimestrePrueba Es el bimestre que se debe considerar para mostrar las notas.
     * @param contextoApp Contiene las configuraciones del contexto de la aplicación que permite definir los accesos a la BD mediante repositorios.
     */
    private static void mostrarNotasDeEstudiantes(List<Estudiante> listadoEstudiantes, int anioPrueba,
            String bimestrePrueba, ConfigurableApplicationContext contextoApp) {
        I_NotaRepository notasRepo = contextoApp.getBean(NotaRepository.class);
        I_AsignaturaRepository asignaturaRepo = contextoApp.getBean(AsignaturaRepository.class);

        System.out.println("[ Notas de los estudiantes del grado para el año " + anioPrueba + " y bimestre "
                + bimestrePrueba + " ]");
        listadoEstudiantes.forEach(estudiante -> {
            List<Nota> notasPorEstudiante = new ArrayList<>();
            try {
                notasPorEstudiante = notasRepo.findByEstudiante(estudiante.getIdEstudiante());
            } catch (Exception e) {
                System.err.println("** ERROR ** al intentar recuperar las notas del estudiante_id: "
                        + estudiante.getIdEstudiante());
                System.err.println(e.getMessage());
            }
            if (notasPorEstudiante.size() > 0) {
                System.out.println("--> [ " + estudiante.getNombre() + " " + estudiante.getApellido() + " ]");
                notasPorEstudiante.forEach(nota -> {
                    if (nota.getAnio() == anioPrueba && nota.getBimestre().getDbValue().equals(bimestrePrueba)) {
                        try {
                            System.out.println(
                                    "--> " + asignaturaRepo.findById(nota.getIdAsignatura()).getNombreAsignatura()
                                            + ": " + nota.getNota());
                        } catch (Exception e) {
                            System.err.println(
                                    "** ERROR ** al intentar recuperar asignatura_id: : " + nota.getIdAsignatura());
                            System.err.println(e.getMessage());
                        }
                    }

                });
                System.out.println();
            }
        });
        System.out.println();
    }

    /**
     * Muestra los datos de un grado definido para pruebas. Se utilizan variables
     * para definir nombre y turno de grado a probar.
     * 
     * @param nombreGradoPrueba Es el nombre del grado del que se quiere ejecutar la
     *                          prueba.
     * @param turnoGradoPrueba  Es el turno del grado del que se quiere ejecutar la
     *                          prueba.
     * @param contexto          Contiene las configuraciones del contexto de la
     *                          aplicación que permite definir los accesos a la BD
     *                          mediante repositorios.
     * @return Devuelve el listado de estudiantes pertenecientes al grado.
     */
    private static List<Estudiante> mostrarDatosDelGrado(String nombreGradoPrueba, String turnoGradoPrueba,
            ConfigurableApplicationContext contexto) {
        I_EstudianteRepository estudianteRepo = contexto.getBean(EstudianteRepository.class);
        I_GradoRepository gradoRepo = contexto.getBean(GradoRepository.class);
        List<Estudiante> listadoEstudiantes = new ArrayList<>();

        try {
            listadoEstudiantes = estudianteRepo
                    .findByGrado(gradoRepo.findByNombreYTurno(nombreGradoPrueba, turnoGradoPrueba).getIdGrado());
            System.out.println("[ Datos de grado a en prueba ]");
            System.out.println("--> Nombre de grado: "
                    + gradoRepo.findByNombreYTurno(nombreGradoPrueba, turnoGradoPrueba).getNombreGrado().getDbValue());
            System.out.println("--> Turno: " + turnoGradoPrueba);
            System.out.println("--> Docente a cargo: "
                    + gradoRepo.findByNombreYTurno(nombreGradoPrueba, turnoGradoPrueba).getDocente());
            System.out.println("--> Total de estudiantes: " + listadoEstudiantes.size());
            System.out.println();
        } catch (SQLException e) {
            System.err.println("** ERROR ** Se detecto un error al intentar mostrar los datos del grado a probar");
            System.err.println(e.getMessage());
        }
        return listadoEstudiantes;
    }

    /**
     * Muestra un cartel por consola para indicar que inician las pruebas
     */
    private static void mostrarCartelInicio() {
        System.out.println();
        System.out.println(" *********************");
        System.out.println("[ INICIAN LAS PRUEBAS ]");
        System.out.println(" *********************");
        System.out.println();
    }

    /**
     * Muestra un cartel por consola para indicar que finalizan las pruebas
     */
    private static void mostrarCartelFinal() {
        System.out.println();
        System.out.println(" *********************");
        System.out.println("[ FINALIZAN LAS PRUEBAS ]");
        System.out.println(" *********************");
        System.out.println();
    }

}
