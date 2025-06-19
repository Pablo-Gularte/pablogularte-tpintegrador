USE escuela;

-- #1 Muestro cuántos estudiantes hay por grado y turno
SELECT 
  COUNT(e.id_estudiante) estudiantes, 
  g.nombre_grado grado, 
  g.turno 
FROM 
  estudiantes e 
  JOIN grados g ON g.id_grado = e.id_grado 
GROUP BY 
  g.nombre_grado, 
  g.turno 
ORDER BY 
  turno, 
  grado;
  
-- #2 Total de estudiantes por turno
SELECT 
  COUNT(e.id_estudiante) total_estudiantes, 
  g.turno 
FROM 
  estudiantes e 
  JOIN grados g ON g.id_grado = e.id_grado 
GROUP BY 
  g.turno 
ORDER BY 
  turno;
  
-- #3 Muestro la asistencia de un estudiante dado (id_estudiante = 15) en un mes dado (junio)
SELECT 
  e.nombre, 
  e.apellido, 
  a.fecha, 
  a.tipo_asistencia 
FROM 
  asistencias a 
  JOIN estudiantes e ON e.id_estudiante = a.id_estudiante 
WHERE 
  e.id_estudiante = 15 
  AND MONTH(a.fecha) = '06';
  
-- #4 Muestro la asistencia agrupada por tipo para el caso anterior
SELECT 
  a.tipo_asistencia asistencia, 
  COUNT(a.fecha) cantidad 
FROM 
  asistencias a 
  JOIN estudiantes e ON e.id_estudiante = a.id_estudiante 
WHERE 
  e.id_estudiante = 15 
  AND MONTH(a.fecha) = '06' 
GROUP BY 
  a.tipo_asistencia;
  
-- #5 Muestro las notas de un estudiante dado (id_estudiante = 15) para un año dado (anio = 2024)
select 
  e.nombre, 
  e.apellido, 
  n.nota, 
  a.nombre_asignatura, 
  n.bimestre 
from 
  notas n 
  join estudiantes e on e.id_estudiante = n.id_estudiante 
  join asignaturas a on a.id_asignatura = n.id_asignatura 
where 
  e.id_estudiante = 15 
  and n.anio = 2024 
order by 
  n.bimestre, 
  e.id_estudiante, 
  a.id_asignatura;
  
-- #6 Muestro el detalle de notas de un estudiante dado (id_estudiante = 15) para una materia dada (nombre_asignatura = 'Matemática') duarante un año dado (anio = 2024)
SELECT 
  e.nombre, 
  e.apellido, 
  n.nota, 
  a.nombre_asignatura materia, 
  n.bimestre 
FROM 
  notas n 
  JOIN estudiantes e ON e.id_estudiante = n.id_estudiante 
  JOIN asignaturas a ON a.id_asignatura = n.id_asignatura 
WHERE 
  e.id_estudiante = 15 
  AND nombre_asignatura = 'Matemática' 
  AND n.anio = 2024 
ORDER BY 
  n.bimestre, 
  e.id_estudiante, 
  a.id_asignatura;
