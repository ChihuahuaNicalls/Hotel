--Eliminar tablas si existen
DROP TABLE IF EXISTS Atiende;
DROP TABLE IF EXISTS Reserva_Servicio;
DROP TABLE IF EXISTS TelefonoEmpleado;
DROP TABLE IF EXISTS EmailEmpleado;
DROP TABLE IF EXISTS TelefonoCliente;
DROP TABLE IF EXISTS EmailCliente;
DROP TABLE IF EXISTS Reserva;
DROP TABLE IF EXISTS Habitacion;
DROP TABLE IF EXISTS Servicio;
DROP TABLE IF EXISTS Categoria;
DROP TABLE IF EXISTS Empleado;
DROP TABLE IF EXISTS Area;
DROP TABLE IF EXISTS Cliente;

--Crear tablas
CREATE TABLE Cliente (
    cedulaCliente INTEGER PRIMARY KEY,
    primerNombre VARCHAR(100) NOT NULL,
    segundoNombre VARCHAR(100),
    primerApellido VARCHAR(100) NOT NULL,
    segundoApellido VARCHAR(100),
    calle VARCHAR(100) NOT NULL,
    carrera VARCHAR(100) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100)
);

CREATE TABLE Area (
    idArea INTEGER PRIMARY KEY,
    nombreArea VARCHAR(100) NOT NULL
);

CREATE TABLE Empleado (
    cedulaEmpleado INTEGER PRIMARY KEY,
    primerNombre VARCHAR(100) NOT NULL,
    segundoNombre VARCHAR(100),
    primerApellido VARCHAR(100) NOT NULL,
    segundoApellido VARCHAR(100),
    calle VARCHAR(100) NOT NULL,
    carrera VARCHAR(100) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    cargo VARCHAR(100) NOT NULL,
    idArea INTEGER NOT NULL,
    FOREIGN KEY (idArea) REFERENCES Area(idArea)
);

CREATE TABLE Categoria (
    idCategoria INTEGER PRIMARY KEY,
    nombreCategoria VARCHAR(100) NOT NULL,
    descCategoria VARCHAR(250),
    precioCategoria NUMERIC NOT NULL
);

CREATE TABLE Habitacion (
    idHabitacion INTEGER PRIMARY KEY,
    idCategoria INTEGER NOT NULL,
    numeroHabitacion INTEGER UNIQUE NOT NULL,
    piso INTEGER NOT NULL,
    descripcion VARCHAR(255) DEFAULT 'Sin descripcion',
    mantenimiento BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (idCategoria) REFERENCES Categoria(idCategoria)
);

CREATE TABLE Servicio (
    idServicio INTEGER PRIMARY KEY,
    nombreServicio VARCHAR(100) NOT NULL,
    detalleServicio VARCHAR(250),
    precioServicio NUMERIC NOT NULL
);

CREATE TABLE TelefonoCliente (
    cedulaCliente INTEGER,
    telefonoCliente VARCHAR(20),
    PRIMARY KEY (cedulaCliente, telefonoCliente),
    FOREIGN KEY (cedulaCliente) REFERENCES Cliente(cedulaCliente) ON DELETE CASCADE
);

CREATE TABLE EmailCliente (
    cedulaCliente INTEGER,
    correoCliente VARCHAR(150),
    PRIMARY KEY (cedulaCliente, correoCliente),
    FOREIGN KEY (cedulaCliente) REFERENCES Cliente(cedulaCliente) ON DELETE CASCADE
);

CREATE TABLE TelefonoEmpleado (
    cedulaEmpleado INTEGER,
    telefonoEmpleado VARCHAR(20),
    PRIMARY KEY (cedulaEmpleado, telefonoEmpleado),
    FOREIGN KEY (cedulaEmpleado) REFERENCES Empleado(cedulaEmpleado) ON DELETE CASCADE
);

CREATE TABLE EmailEmpleado (
    cedulaEmpleado INTEGER,
    correoEmpleado VARCHAR(150),
    PRIMARY KEY (cedulaEmpleado, correoEmpleado),
    FOREIGN KEY (cedulaEmpleado) REFERENCES Empleado(cedulaEmpleado) ON DELETE CASCADE
);

CREATE TABLE Reserva (
    cedulaCliente INTEGER,
    idHabitacion INTEGER,
    fechaLlegada TIMESTAMP,
    fechaSalida TIMESTAMP NOT NULL,
    PRIMARY KEY (cedulaCliente, idHabitacion, fechaLlegada),
    FOREIGN KEY (cedulaCliente) REFERENCES Cliente(cedulaCliente) ON DELETE CASCADE, 
    FOREIGN KEY (idHabitacion) REFERENCES Habitacion(idHabitacion)
);

CREATE TABLE Reserva_Servicio (
    idServicio INTEGER,
    fechaLlegada TIMESTAMP,
    cedulaCliente INTEGER,
    idHabitacion INTEGER,
    fechaUso TIMESTAMP NOT NULL,
    PRIMARY KEY (fechaUso, fechaLlegada, cedulaCliente, idHabitacion, idServicio),
    FOREIGN KEY (cedulaCliente, idHabitacion, fechaLlegada) REFERENCES Reserva(cedulaCliente, idHabitacion, fechaLlegada) ON DELETE CASCADE,
    FOREIGN KEY (idServicio) REFERENCES Servicio(idServicio) ON DELETE CASCADE
);

CREATE TABLE Atiende (
    cedulaCliente INTEGER,
    cedulaEmpleado INTEGER,
    idHabitacion INTEGER,
    idServicio INTEGER,
    fechaLlegada TIMESTAMP,
    fechaUso TIMESTAMP,
    PRIMARY KEY (cedulaCliente, cedulaEmpleado, fechaUso, fechaLlegada, idHabitacion, idServicio),
    FOREIGN KEY (cedulaEmpleado) REFERENCES Empleado(cedulaEmpleado) ON DELETE CASCADE,
    FOREIGN KEY (fechaUso, fechaLlegada, cedulaCliente, idHabitacion, idServicio) REFERENCES Reserva_Servicio(fechaUso, fechaLlegada, cedulaCliente, idHabitacion, idServicio) ON DELETE CASCADE
);

--Checks
ALTER TABLE Cliente ADD CONSTRAINT CK_CedulaCliente CHECK(cedulaCliente > 0);
ALTER TABLE Cliente ADD CONSTRAINT CK_PrimerNombreCliente CHECK(primerNombre ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Cliente ADD CONSTRAINT CK_SegundoNombreCliente CHECK(segundoNombre IS NULL OR segundoNombre ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Cliente ADD CONSTRAINT CK_PrimerApellidoCliente CHECK(primerApellido ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Cliente ADD CONSTRAINT CK_SegundoApellidoCliente CHECK(segundoApellido IS NULL OR segundoApellido ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE TelefonoCliente ADD CONSTRAINT CK_TelefonoCliente CHECK(telefonoCliente ~ '^[0-9+\- ()]+$');
ALTER TABLE EmailCliente ADD CONSTRAINT CK_CorreoCliente CHECK(correoCliente ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
ALTER TABLE Empleado ADD CONSTRAINT CK_CedulaEmpleado CHECK(cedulaEmpleado > 0);
ALTER TABLE Empleado ADD CONSTRAINT CK_PrimerNombreEmpleado CHECK(primerNombre ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Empleado ADD CONSTRAINT CK_SegundoNombreEmpleado CHECK(segundoNombre IS NULL OR segundoNombre ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Empleado ADD CONSTRAINT CK_PrimerApellidoEmpleado CHECK(primerApellido ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Empleado ADD CONSTRAINT CK_SegundoApellidoEmpleado CHECK(segundoApellido IS NULL OR segundoApellido ~ '^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$');
ALTER TABLE Empleado ADD CONSTRAINT CK_CargoEmpleado CHECK(LENGTH(cargo) >= 3);
ALTER TABLE TelefonoEmpleado ADD CONSTRAINT CK_TelefonoEmpleado CHECK(telefonoEmpleado ~ '^[0-9+\- ()]+$');
ALTER TABLE EmailEmpleado ADD CONSTRAINT CK_CorreoEmpleado CHECK(correoEmpleado ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
ALTER TABLE Area ADD CONSTRAINT CK_IdArea CHECK(idArea > 0);
ALTER TABLE Area ADD CONSTRAINT CK_NombreArea CHECK(LENGTH(nombreArea) >= 3);
ALTER TABLE Categoria ADD CONSTRAINT CK_IdCategoria CHECK(idCategoria > 0);
ALTER TABLE Categoria ADD CONSTRAINT CK_NombreCategoria CHECK(LENGTH(nombreCategoria) >= 3);
ALTER TABLE Categoria ADD CONSTRAINT CK_PrecioCategoria CHECK(precioCategoria >= 0);
ALTER TABLE Habitacion ADD CONSTRAINT CK_IdHabitacion CHECK(idHabitacion > 0);
ALTER TABLE Habitacion ADD CONSTRAINT CK_NumHabit CHECK(numeroHabitacion > 0);
ALTER TABLE Habitacion ADD CONSTRAINT CK_Piso CHECK(piso >= 0);
ALTER TABLE Servicio ADD CONSTRAINT CK_IdServicio CHECK(idServicio > 0);
ALTER TABLE Servicio ADD CONSTRAINT CK_NombreServicio CHECK(LENGTH(nombreServicio) >= 3);
ALTER TABLE Servicio ADD CONSTRAINT CK_PrecioServicio CHECK(precioServicio >= 0);
ALTER TABLE Reserva ADD CONSTRAINT CK_FechasReserva CHECK(fechaSalida > fechaLlegada);
ALTER TABLE Reserva_Servicio ADD CONSTRAINT CK_FechaUsoServicio CHECK (fechaUso >= CURRENT_TIMESTAMP - INTERVAL '1 year');
ALTER TABLE Atiende ADD CONSTRAINT CK_Atiende_Fechas CHECK(fechaUso >= fechaLlegada);

--Triggers y funciones
--Validar mantenimiento
CREATE OR REPLACE FUNCTION validarMantenimiento()
RETURNS TRIGGER AS $$
DECLARE enMantenimiento BOOLEAN;
BEGIN
    SELECT mantenimiento INTO enMantenimiento
    FROM Habitacion
    WHERE idHabitacion = NEW.idHabitacion;

    IF enMantenimiento THEN
        RAISE EXCEPTION 'La habitacion % esta en mantenimiento.', NEW.idHabitacion;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trgValidarMantenimiento
BEFORE INSERT OR UPDATE ON Reserva
FOR EACH ROW
EXECUTE FUNCTION validarMantenimiento();

--Validar disponibilidad
CREATE OR REPLACE FUNCTION validarDisponibilidadHabitacion()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM Reserva r
        WHERE r.idHabitacion = NEW.idHabitacion
          AND r.fechaLlegada < NEW.fechaSalida
          AND r.fechaSalida > NEW.fechaLlegada
    ) THEN
        RAISE EXCEPTION 'La habitacion % no esta disponible en esas fechas.', NEW.idHabitacion;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trgValidarDisponibilidadHabitacion
BEFORE INSERT ON Reserva
FOR EACH ROW
EXECUTE FUNCTION validarDisponibilidadHabitacion();

--Validar servicio despues de llegada
CREATE OR REPLACE FUNCTION validarFechaServicio()
RETURNS TRIGGER AS $$
DECLARE fecha_llegada TIMESTAMP;
BEGIN
    SELECT fechaLlegada INTO fecha_llegada
    FROM Reserva
    WHERE cedulaCliente = NEW.cedulaCliente
      AND idHabitacion = NEW.idHabitacion
      AND fechaLlegada = NEW.fechaLlegada;

    IF NEW.fechaUso < fecha_llegada THEN
        RAISE EXCEPTION 'El servicio no puede usarse antes de la llegada.';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trgValidarFechaServicio
BEFORE INSERT ON Reserva_Servicio
FOR EACH ROW
EXECUTE FUNCTION validarFechaServicio();