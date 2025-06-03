-- Selecciono la base de datos a utilizar
USE escuela;

-- #1 Listar los totales de estudiantes por ciclo y por turno
SELECT g.turno,
       g.ciclo,
       count(e.id_estudiante) estudiantes
FROM grados g,
     estudiantes e
WHERE e.id_grado = g.id_grado
GROUP BY g.turno,
         g.ciclo;

-- #2 Listar los grados con docentes y total de estudiantes, ordenados por turno y grado
SELECT g.turno,
       g.nombre_grado grado,
       g.docente,
       count(e.id_estudiante) estudiantes,
       g.id_grado
FROM grados g,
     estudiantes e
WHERE e.id_grado = g.id_grado
GROUP BY g.nombre_grado,
         g.turno,
         g.docente,
         g.id_grado
ORDER BY g.turno,
         g.nombre_grado;

-- #3 Listar detalle de estudiantes por grado
SELECT e.nombre,
       e.apellido,
       e.activo,
       e.edad,
       e.id_estudiante
FROM estudiantes e
WHERE e.id_grado = 8
ORDER BY e.apellido;

-- #4 Listar detalle de asistencia por grado y fecha
SELECT e.apellido,
       e.nombre,
       a.tipo_asistencia,
       a.fecha
FROM asistencias a,
     estudiantes e
WHERE e.id_estudiante = a.id_estudiante
  AND e.id_grado = 8
  AND a.fecha    = '2024-09-01'
ORDER BY e.apellido;

-- #5 Listar agrupado de asistencia por fecha y turno
SELECT g.nombre_grado,
       a.tipo_asistencia,
       a.fecha,
       count(e.id_estudiante) estudiantes
FROM asistencias a,
     estudiantes e,
     grados g
WHERE g.id_grado = e.id_grado
  AND e.id_estudiante = a.id_estudiante
  AND g.turno = 'Mañana'
  AND a.fecha = '2024-09-01'
GROUP BY g.nombre_grado,
         a.tipo_asistencia,
         a.fecha;

-- #6 Listar detalle de notas de cada bimestre por estudiante y año, ordenado por bimestre
SELECT n.bimestre,
       e.apellido,
       e.nombre,
       g.nombre_grado grado,
       g.turno,
       n.nota,
       n.anio
FROM notas n,
     estudiantes e,
     grados g
WHERE g.id_grado      = e.id_grado
  AND e.id_estudiante = n.id_estudiante
  AND n.id_estudiante = 1
  AND n.anio          = 2024 ;
  
  -- #7 Listar detalle de notas por grado, bimestre y año
  Select e.apellido,
         e.nombre,
         n.nota,
         n.bimestre
   From notas n,
        estudiantes e
 Where n.id_estudiante = e.id_estudiante
   And n.bimestre = 'Primer bimestre'
   And n.anio     = 2025
   And e.id_grado = 2;
   
  -- #8 Listar detalle de asistencia por fecha y grado
  Select a.tipo_asistencia,
         e.apellido,
         e.nombre
   From asistencias a,
        estudiantes e
 Where e.id_estudiante = a.id_estudiante
   and e.id_grado = 1
   and a.fecha = '2024-09-01';