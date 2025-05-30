-- Selecciono la base de datos a utilizar
USE escuela;

-- #1 Listar los totales de estudiantes por ciclo y por turno
SELECT g.turno,
		g.ciclo,
        count(e.id_estudiante) estudiantes
  FROM grados g,
		estudiantes e
where e.id_grado = g.id_grado
group by g.turno,
		g.ciclo;

-- #2 Listar los grados con docentes y total de estudiantes, ordenados por turno y grado
SELECT g.turno,
		g.nombre_grado grado,
		g.docente,
        count(e.id_estudiante) estudiantes,
        g.id_grado
  FROM grados g,
		estudiantes e
where e.id_grado = g.id_grado
group by g.nombre_grado,
		g.turno,
		g.docente,
        g.id_grado
ORDER BY g.turno, g.nombre_grado;

-- #3 Listar detalle de estudiantes por grado
Select e.nombre,
		e.apellido,
        e.activo,
        e.edad,
        e.id_estudiante
from estudiantes e
Where e.id_grado = 8
order by e.apellido;

-- #4 Listar detalle de asistencia por grado y fecha
Select e.apellido,
		e.nombre,
        a.tipo_asistencia,
        a.fecha
from asistencias a,
	estudiantes e
where e.id_estudiante = a.id_estudiante
and a.id_grado = 8
and a.fecha = '2024-09-01'
order by e.apellido;

-- #5 Listar agrupado de asistencia por grado, fecha y turno
Select g.nombre_grado,
		a.tipo_asistencia,
        a.fecha,
        count(e.id_estudiante) estudiantes
from asistencias a,
	estudiantes e,
    grados g
where g.id_grado = a.id_grado
and e.id_estudiante = a.id_estudiante
and g.turno = 'Mañana'
and a.fecha = '2024-09-01'
group by g.nombre_grado,
		a.tipo_asistencia,
        a.fecha;

-- #6 Listar detalle de notas de cada bimestre por estudiante y año, ordenado por bimestre
Select n.bimestre,
		e.apellido,
        e.nombre,
        g.nombre_grado grado,
        g.turno,
        n.nota
from notas n,
	estudiantes e,
    grados g
where g.id_grado = n.id_grado
and e.id_estudiante = n.id_estudiante
and n.id_estudiante = 1
and n.anio = 2024
;