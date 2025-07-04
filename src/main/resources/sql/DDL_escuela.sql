-- ----------------------------
-- Crear base de datos escuela
-- ----------------------------
DROP DATABASE IF EXISTS escuela;
CREATE DATABASE escuela;
USE escuela ;

-- -----------------------------------------------------
-- Crear tabla grados
-- -----------------------------------------------------
DROP TABLE IF EXISTS grados ;

CREATE TABLE IF NOT EXISTS grados (
  id_grado INT AUTO_INCREMENT,
  nombre_grado ENUM('Primero', 'Segundo', 'Tercero', 'Cuarto', 'Quinto', 'Sexto', 'Séptimo') NOT NULL,
  ciclo ENUM('Primer ciclo', 'Segundo ciclo') NOT NULL,
  turno ENUM('Mañana', 'Tarde', 'Jornada completa') NOT NULL,
  docente VARCHAR(100) NOT NULL,
  activo TINYINT(1) NOT NULL,
  PRIMARY KEY (id_grado))
;


-- -----------------------------------------------------
-- Crear tabla estudiantes
-- -----------------------------------------------------
DROP TABLE IF EXISTS estudiantes ;

CREATE TABLE IF NOT EXISTS estudiantes (
  id_estudiante INT AUTO_INCREMENT,
  id_grado INT NOT NULL,
  nombre VARCHAR(45) NOT NULL,
  apellido VARCHAR(45) NOT NULL,
  edad INT NOT NULL DEFAULT '6',
  direccion VARCHAR(200) NOT NULL,
  nombre_madre VARCHAR(100) NULL DEFAULT NULL,
  nombre_padre VARCHAR(100) NULL DEFAULT NULL,
  hermano_en_escuela TINYINT(1) NOT NULL DEFAULT '0',
  activo TINYINT(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (id_estudiante);

ALTER TABLE estudiantes
ADD CONSTRAINT fk_estudiantes_grados
    FOREIGN KEY (id_grado)
    REFERENCES grados (id_grado);


-- -----------------------------------------------------
-- Crear tabla asistencias
-- -----------------------------------------------------
DROP TABLE IF EXISTS asistencias ;

CREATE TABLE IF NOT EXISTS asistencias (
  id_asistencia INT AUTO_INCREMENT,
  fecha DATE NOT NULL,
  id_estudiante INT NOT NULL,
  tipo_asistencia ENUM('Presente', 'Ausente', 'Llegada tarde') NOT NULL,
  PRIMARY KEY (id_asistencia);

ALTER TABLE asistencias
ADD CONSTRAINT fk_asistencias_estudiantes
    FOREIGN KEY (id_estudiante)
    REFERENCES estudiantes (id_estudiante);


-- -----------------------------------------------------
-- Crear tabla asignaturas
-- -----------------------------------------------------
DROP TABLE IF EXISTS asignaturas ;

CREATE TABLE IF NOT EXISTS asignaturas (
  id_asignatura INT AUTO_INCREMENT,
  nombre_asignatura VARCHAR(45),
  docente VARCHAR(45),
  PRIMARY KEY (id_asignatura));


-- -----------------------------------------------------
-- Crear tabla notas
-- -----------------------------------------------------
DROP TABLE IF EXISTS notas ;

CREATE TABLE IF NOT EXISTS notas (
  id_nota INT AUTO_INCREMENT,
  nota INT NOT NULL,
  anio INT NOT NULL,
  bimestre ENUM('Primer bimestre', 'Segundo bimestre', 'Tercer bimestre', 'Cuarto bimestre') NOT NULL,
  id_estudiante INT NOT NULL,
  id_asignatura INT NOT NULL,
  notascol VARCHAR(45) NOT NULL,
  PRIMARY KEY (id_nota);

ALTER TABLE notas
ADD CONSTRAINT fk_notas_estudiantes
    FOREIGN KEY (id_estudiante)
    REFERENCES estudiantes (id_estudiante),
ADD CONSTRAINT fk_notas_asignaturas
    FOREIGN KEY (id_asignatura)
    REFERENCES asignaturas (id_asignatura)
;