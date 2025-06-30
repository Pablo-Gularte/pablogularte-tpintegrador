USE escuela;        -- Selecciono la BD.

-- #1 Cargar datos de grados y docentes
insert into grados (nombre_grado, ciclo, turno, docente) values
('Primero', 'Primer ciclo', 'Mañana', 'Matías Ruiz'),
('Segundo', 'Primer ciclo', 'Mañana', 'Vanesa Leiva'),
('Tercero', 'Primer ciclo', 'Mañana', 'Julieta Pérez'),
('Cuarto', 'Segundo ciclo', 'Mañana', 'Adriana Arias'),
('Quinto', 'Segundo ciclo', 'Mañana', 'Pedro González'),
('Sexto', 'Segundo ciclo', 'Mañana', 'Daiana Giménez'),
('Séptimo', 'Segundo ciclo', 'Mañana', 'Roxana Costa');

-- #2 Cargar datos de asignaturas
insert into asignaturas (nombre_asignatura, docente) values
('Lengua','Cecilia Pérez'),
('Matemática','Leonardo Paz'),
('Conocimiento del Mundo','Estéban Quito'),
('Ciencias Naturales','Lorena Casas'),
('Ciencias Sociales','Mercedes Velázco'),
('Inglés','Romina Martínez'),
('Francés,','Miriam Páez'),
('Italiano','Lorena Di Giacomo'),
('Portugués.','Juan Da Silva'),
('Artes Visuales','Valeria Arias'),
('Música','Ramiro Quiroga'),
('Teatro','Teresa Martínez'),
('Educación Física','Marcelo Sosa'),
('Tecnologías, Diseño y Programación','Cintia Mendoza');

-- #3 Cargar datos de estudiantes 
insert into estudiantes (nombre, apellido, id_grado, edad, direccion, nombre_madre, nombre_padre, hermano_en_escuela) Select 'Agustín' nombre, 'Morales' apellido, id_grado, 6 edad, 'Calle Mitre 63' direccion, 'Agustina Ramírez' nombre_madre, 'Matías Sánchez' nombre_padre, FALSE hermano_en_escuela from grados where nombre_grado = 'Primero' and turno = 'Mañana';
insert into estudiantes (nombre, apellido, id_grado, edad, direccion, nombre_madre, nombre_padre, hermano_en_escuela) Select 'Antonella' nombre, 'Rodríguez' apellido, id_grado, 6 edad, 'Calle Belgrano 825' direccion, 'Antonella Jiménez' nombre_madre, 'Lucas Gómez' nombre_padre, FALSE hermano_en_escuela from grados where nombre_grado = 'Primero' and turno = 'Mañana';
insert into estudiantes (nombre, apellido, id_grado, edad, direccion, nombre_madre, nombre_padre, hermano_en_escuela) Select 'Agustina' nombre, 'Gómez' apellido, id_grado, 7 edad, 'Calle Hipólito Yrigoyen 907' direccion, 'Catalina Flores' nombre_madre, 'Diego Gómez' nombre_padre, FALSE hermano_en_escuela from grados where nombre_grado = 'Primero' and turno = 'Mañana';
insert into estudiantes (nombre, apellido, id_grado, edad, direccion, nombre_madre, nombre_padre, hermano_en_escuela) Select 'Florencia' nombre, 'Vargas' apellido, id_grado, 6 edad, 'Calle España 364' direccion, 'Julieta Romero' nombre_madre, 'Benjamín Rivera' nombre_padre, FALSE hermano_en_escuela from grados where nombre_grado = 'Primero' and turno = 'Mañana';


-- #4 Cargar asistencias
INSERT INTO asistencias (fecha, id_estudiante, tipo_asistencia) VALUES 
('2024-2-26', 1, 'Presente'),
('2024-2-26', 2, 'Llegada tarde'),
('2024-2-26', 3, 'Ausente'),
('2024-2-26', 4, 'Presente'),

-- #5 Cargar notas
INSERT INTO NOTAS (nota, anio, bimestre, id_estudiante, id_asignatura) VALUES 
(1, 2024, 'Primer bimestre', 1, 1),
(4, 2024, 'Segundo bimestre', 1, 1),
(5, 2024, 'Tercer bimestre', 1, 1),
(8, 2024, 'Cuarto bimestre', 1, 1),