package ar.org.curso.centro8.java.tests.pruebaspropias.testrepositorios;

import java.util.List;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ar.org.curso.centro8.java.models.entities.Nota;
import ar.org.curso.centro8.java.models.repositories.NotaRepository;
import ar.org.curso.centro8.java.tests.pruebaspropias.ConfiguracionBD;

public class TestNotaRepository {
    public static void main(String[] args) {
        HikariConfig config = ConfiguracionBD.getConfiguracion();
        
        try (HikariDataSource ds = new HikariDataSource(config)) {
            NotaRepository notaRepository = new NotaRepository(ds);
            int idNota = 1;

            System.out.println("Prueba de impresión de una nota por ID: " + idNota);
            var nota = notaRepository.findById(idNota);
            if (nota != null) {
                System.out.println(nota);
            } else {
                System.out.println("No se encontró la nota con ID " + idNota);
            }

            List<Nota> notas = notaRepository.findAll();
            int anio = 2025;
            int estudianteId = 1;
            System.out.println("\nPrueba de impresión de todas las notas del año " + anio + " para el estudiante con ID " + estudianteId);
            notas.stream()
                .filter(n -> n.getAnio() == anio && n.getIdEstudiante() == estudianteId)
                .forEach(System.out::println);
            System.out.println();
            
        } catch (Exception e) {
            System.out.println("ERROR al conectar con la base de datos: " + e.getMessage());
        }
    }
}
