package ar.org.curso.centro8.java.tests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ar.org.curso.centro8.java.entities.Estudiante;
import ar.org.curso.centro8.java.repositories.EstudianteRepository;
import ar.org.curso.centro8.java.repositories.GradoRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_EstudianteRepository;
import ar.org.curso.centro8.java.repositories.interfaces.I_GradoRepository;

@SpringBootApplication(scanBasePackages = "ar.org.curso.centro8.java")
public class TestRepositories {
    public static void main(String[] args) {
        try (ConfigurableApplicationContext context = SpringApplication.run(TestRepositories.class, args)) {
            // Defino los valores de las variables para las pruebas
            I_EstudianteRepository estudianteRepo = context.getBean(EstudianteRepository.class);
            I_GradoRepository gradoRepo = context.getBean(GradoRepository.class);
            String nombreGradoPrueba = "Tercero";

            // Muestro cartel de inicio de pruebas en pantalla
            System.out.println();
            System.out.println(" *********************");
            System.out.println("[ INICIAN LAS PRUEBAS ]");
            System.out.println(" *********************");
            System.out.println();

            // Muestro los datos del grado indicado.

            // Muestro las notas de los estudiantes del grado indicado.

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
