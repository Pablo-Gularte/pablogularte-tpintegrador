-- -----------------------------------------------------
-- Crear la BD escuela
--
-- Esquema de base de datos "escuela" para el TP del curso de "Programador JAVA" del CFP 8 SMATA
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS escuela;
USE escuela ;

-- -----------------------------------------------------
-- Crear la tabla grados
-- -----------------------------------------------------
DROP TABLE IF EXISTS grados ;

CREATE TABLE IF NOT EXISTS grados (
  id_grado INT NOT NULL AUTO_INCREMENT COMMENT 'Clave primaria de la tabla "grados"',
  nombre_grado ENUM('Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto', 'Séptimo') NOT NULL DEFAULT 'Primero' COMMENT 'Indica el nombre del grado de la  Por defecto toma primer grado como valor inicial.',
  ciclo ENUM('Primer ciclo', 'Segundo ciclo') NOT NULL DEFAULT 'Primer ciclo' COMMENT 'Indica a qué ciclo de la escuela primaria corresponde el grado (Primer ciclo: 1° a 3°; Segundo ciclo: 4° a 7°)',
  turno ENUM('Mañana', 'Tarde', 'Jornada completa') NULL,
  docente VARCHAR(100) NULL COMMENT 'Indica el nombre de docente a cargo del grado',
  activo TINYINT NOT NULL DEFAULT 1 COMMENT 'Indica si el grado está activo y con estudiantes matriculados. Por defecto se asume que está activo.',
  PRIMARY KEY (id_grado))
ENGINE = InnoDB;

-- -- Crear índices de la tabla grados
CREATE INDEX idx_grados_nombre ON grados (nombre_grado);
CREATE INDEX idx_grados_docente ON grados (docente);
CREATE INDEX idx_grados_activo ON grados (activo);


-- -----------------------------------------------------
-- Crear la tabla estudiantes
-- -----------------------------------------------------
DROP TABLE IF EXISTS estudiantes ;

CREATE TABLE IF NOT EXISTS estudiantes (
  id_estudiante INT NOT NULL AUTO_INCREMENT COMMENT 'Clave primaria de la tabla "estudiantes"',
  id_grado INT NULL COMMENT 'Clave foránea que relaciona con la tabla "grados". Indica a qué grado pertenece el estudiante.',
  nombre VARCHAR(45) NOT NULL COMMENT 'Nombre de estudiante',
  apellido VARCHAR(45) NOT NULL COMMENT 'Apellido de estudiante',
  edad INT NOT NULL DEFAULT 6 COMMENT 'Edad de estudiante',
  direccion VARCHAR(200) NOT NULL COMMENT 'Dirección correspondiente a estudiante',
  nombre_madre VARCHAR(100) NULL COMMENT 'Nombre de la madre de estudiante',
  nombre_padre VARCHAR(100) NULL COMMENT 'Nombre del padre de estudiante',
  hermano_en_escuela TINYINT NULL COMMENT 'Valor lógico que indica si el estudiante tiene o no hermano en la escuela',
  activo TINYINT NOT NULL DEFAULT 1 COMMENT 'Este campo indica si el estudiante está activo o no (se cambió de escuela o egresó)',
  PRIMARY KEY (id_estudiante)
  )
ENGINE = InnoDB;

-- -- Crear índices de la tabla estudiantes
CREATE INDEX fk_estudiantes_grados_idx ON estudiantes (id_grado);
CREATE INDEX idx_estudiante_nombre ON estudiantes (apellido);
CREATE INDEX idx_estudiante_apellido ON estudiantes (apellido);
CREATE INDEX idx_estudiante_madre ON estudiantes (nombre_madre);
CREATE INDEX idx_estudiante_padre ON estudiantes (nombre_padre);
CREATE INDEX idx_estudiante_activo ON estudiantes (activo);


-- -----------------------------------------------------
-- Crear la tabla asistencias
-- -----------------------------------------------------
DROP TABLE IF EXISTS asistencias;

CREATE TABLE IF NOT EXISTS asistencias (
  id_asistencia INT NOT NULL AUTO_INCREMENT COMMENT 'Clave primaria de la tabla "ciclo_lectivo"',
  fecha DATE NULL COMMENT 'Indica la fecha de registro de asistencia.',
  id_grado INT NULL COMMENT 'Clave foránea que vincula con la tabla "grados". Indica la correspondencia entre el grado y el ciclo lectivo.',
  id_estudiante INT NULL COMMENT 'Clave foránea que vincula con la tabla "estudiantes". Permite identificar al estudiante.',
  tipo_asistencia ENUM('Presente', 'Ausente', 'Llegada tarde') NOT NULL DEFAULT 'Presente' COMMENT 'Indica la situaciónm de asistencia del estudiante.',
  PRIMARY KEY (id_asistencia)
  )
ENGINE = InnoDB;

-- Crear índices de la tabla asistencias
CREATE INDEX fk_asistencias_estudiantes_idx ON asistencias (id_estudiante);
CREATE INDEX fk_asistencias_grados_idx ON asistencias (id_grado);
CREATE INDEX idx_asistencia_fecha ON asistencias (fecha);


-- -----------------------------------------------------
-- Crear la tabla notas
-- -----------------------------------------------------
DROP TABLE IF EXISTS notas ;

CREATE TABLE IF NOT EXISTS notas (
  id_nota INT NOT NULL AUTO_INCREMENT COMMENT 'Clave primaria de la tabla "notas"',
  anio INT NULL COMMENT 'Indica el año del ciclo lectivo.',
  bimestre ENUM('Primer bimestre', 'Segundo bimestre', 'Tercer bimestre', 'Cuarto bimestre') NOT NULL DEFAULT 'Primer bimestre' COMMENT 'Indica el bimestre correspondiente a la nota',
  id_estudiante INT NULL COMMENT 'Clave foránea que vincula con la tabla "estudiantes". Identifica a qué estudiante corresponde la nota en cuestión.',
  id_grado INT NULL COMMENT 'Clave foránea que vincula con la tabla "grados". Identifica a qué grado pertenece el estudiante de la nota en cuestión.',
  nota float(2,1),
  PRIMARY KEY (id_nota)
  )
ENGINE = InnoDB;

CREATE INDEX fk_notas_estudiantes_idx ON notas (id_estudiante);
CREATE INDEX fk_notas_grados_idx ON notas (id_grado);
CREATE INDEX idx_notas_anio ON notas (anio);
CREATE INDEX idx_notas_bimestre ON notas (bimestre);

-- -----------------------------------------------------
-- Modifico las tablas para agregar las FKs
-- -----------------------------------------------------

-- FKs de tabla estudiantes
ALTER TABLE estudiantes
ADD CONSTRAINT fk_estudiantes_grados
    FOREIGN KEY (id_grado)
    REFERENCES escuela.grados (id_grado)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- FKs de tabla asistencia
ALTER TABLE asistencias
ADD CONSTRAINT fk_asistencias_estudiantes
    FOREIGN KEY (id_estudiante)
    REFERENCES estudiantes (id_estudiante)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;
	
ALTER TABLE asistencias
ADD CONSTRAINT fk_asistencias_grados
    FOREIGN KEY (id_grado)
    REFERENCES grados (id_grado)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;

-- FKs de tabla notas
ALTER TABLE notas
 ADD CONSTRAINT fk_notas_estudiantes
    FOREIGN KEY (id_estudiante)
    REFERENCES estudiantes (id_estudiante)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION;

ALTER TABLE notas
ADD CONSTRAINT fk_notas_grados
    FOREIGN KEY (id_grado)
    REFERENCES grados (id_grado)
    ON DELETE RESTRICT
    ON UPDATE CASCADE;