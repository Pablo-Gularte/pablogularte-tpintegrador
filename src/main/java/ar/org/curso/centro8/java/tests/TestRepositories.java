package ar.org.curso.centro8.java.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.repositories.AsignaturaRepository;
import ar.org.curso.centro8.java.repositories.AsistenciaRepository;
import ar.org.curso.centro8.java.repositories.EstudianteRepository;
import ar.org.curso.centro8.java.repositories.GradoRepository;
import ar.org.curso.centro8.java.repositories.NotaRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_AsignaturaRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_AsistenciaRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_EstudianteRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_GradoRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_NotaRepository;

@SpringBootApplication(scanBasePackages = "ar.org.curso.centro8.java")
public class TestRepositories {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(TestRepositories.class, args)) {
            // Defino los valores de las variables para las pruebas
            I_EstudianteRepository estudianteRepo = context.getBean(EstudianteRepository.class);
            I_GradoRepository gradoRepo = context.getBean(GradoRepository.class);
            I_NotaRepository notasRepo = context.getBean(NotaRepository.class);
            I_AsignaturaRepository asignaturaRepo = context.getBean(AsignaturaRepository.class);
            I_AsistenciaRepository asistenciaRepo = context.getBean(AsistenciaRepository.class);
            String nombreGradoPrueba = "Tercero";
            int anio = 2024;
            String bimestre = "Segundo bimestre";

            // Muestro cartel de inicio de pruebas en pantalla
            System.out.println();
            System.out.println(" *********************");
            System.out.println("[ INICIAN LAS PRUEBAS ]");
            System.out.println(" *********************");
            System.out.println();

            // Muestro los datos del grado indicado.
            System.out.println("[ Datos de grado a en prueba ]");
            System.out.println("--> Nombre de grado: " + gradoRepo.findByNombreGrado(nombreGradoPrueba).getNombreGrado().getDbValue());
            System.out.println("--> Docente a cargo: " + gradoRepo.findByNombreGrado(nombreGradoPrueba).getDocente());
            System.out.println();

            // Muestro las notas de los estudiantes del grado indicado.
            System.out.println("[ Notas de los estudiantes del grado para el año " + anio + " y bimestre " + bimestre + " ]");
            estudianteRepo.findByGrado(gradoRepo.findByNombreGrado(nombreGradoPrueba).getIdGrado()).forEach(e -> {
                System.out.println("--> [ " + e.getNombre() + " " + e.getApellido() + " ]");
            });
            System.out.println();

            // Muestro las asistencias de los estudiantes para el año y período indicados.

            // Creo nuevo estudiante y lo agrego a la BD
            System.out.println("[ CREO nuevo estudiante ]");

            Estudiante nuevoEstudiante = new Estudiante(
                0, 
                gradoRepo.findByNombreGrado(nombreGradoPrueba).getIdGrado(), 
                "Estudiante", 
                "Nuevo", 
                6, 
                "Dirección de estudiante nuevo", 
                "Nombre de la madre de estudiante nuevo", 
                "Nombre del padre de estudiante nuevo", 
                false, 
                true);

            estudianteRepo.create(nuevoEstudiante);
            if (nuevoEstudiante.getIdEstudiante() > 0) {
                System.out.println("--> Creo al estudiante de ID: " + nuevoEstudiante.getIdEstudiante());
                System.out.println(nuevoEstudiante);
                System.out.println("--> Recupero de la BD al estudiante de ID: " + nuevoEstudiante.getIdEstudiante() + " de la BD");
                System.out.println(estudianteRepo.findById(nuevoEstudiante.getIdEstudiante()));
            } else {
                System.out.println("Se detectó un problema al intentar crear el nuevo estudiante.");
            }
            System.out.println();

            // Prueba de modificación de datos

            // Prueba de eliminación de datos

            // Muestor cartel de finalización de pruebas en pantalla
            System.out.println();
            System.out.println(" *********************");
            System.out.println("[ FINALIZAN LAS PRUEBAS ]");
            System.out.println(" *********************");
            System.out.println();
        } catch (Exception e) {
            System.out.println("ESTE MENSAJE es la primera línea que imprime el CATCH");
            System.out.println("ERROR GENERAL detectado durante la prueba de los repositorios con Spring Boot: " + e.getMessage());
        }
    }
}
