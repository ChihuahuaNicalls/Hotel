--Consulta y actualizacion clientes
SELECT c.cedulaCliente, c.primerNombre, c.segundoNombre, c.primerApellido, c.segundoApellido, c.calle, c.carrera, c.numero, c.complemento, tc.telefonoCliente, ec.correoCliente
FROM Cliente c LEFT JOIN TelefonoCliente tc ON c.cedulaCliente = tc.cedulaCliente LEFT JOIN EmailCliente ec ON c.cedulaCliente = ec.cedulaCliente
ORDER BY c.cedulaCliente;

INSERT INTO Cliente(cedulaCliente, primerNombre, segundoNombre, primerApellido, segundoApellido, calle, carrera, numero, complemento) 
VALUES (1021, 'Laura', NULL, 'Gomez', NULL, 'Calle 10', 'Carrera 15', '22-10', NULL);
INSERT INTO TelefonoCliente(cedulaCliente, telefonoCliente) VALUES (1021, '3001239876');
INSERT INTO EmailCliente(cedulaCliente, correoCliente) VALUES (1021, 'laura@example.com');

UPDATE Cliente
SET primerNombre = 'Carlos'
WHERE cedulaCliente = 1003;
UPDATE TelefonoCliente
SET telefonoCliente = '3015558899'
WHERE cedulaCliente = 1003;
UPDATE EmailCliente
SET correoCliente = 'carlosnuevo@example.com'
WHERE cedulaCliente = 1003;

SELECT c.cedulaCliente, c.primerNombre, c.segundoNombre, c.primerApellido, c.segundoApellido, c.calle, c.carrera, c.numero, c.complemento, tc.telefonoCliente, ec.correoCliente
FROM Cliente c LEFT JOIN TelefonoCliente tc ON c.cedulaCliente = tc.cedulaCliente LEFT JOIN EmailCliente ec ON c.cedulaCliente = ec.cedulaCliente
ORDER BY c.cedulaCliente;

--Consulta y actualizacion de reservas
SELECT r.cedulaCliente, r.idHabitacion, r.fechaLlegada, r.fechaSalida
FROM Reserva r
ORDER BY r.cedulaCliente;

INSERT INTO Reserva(cedulaCliente, idHabitacion, fechaLlegada, fechaSalida) 
VALUES (1005, 30, '2025-12-01', '2025-12-05');

UPDATE Reserva
SET fechaLlegada = '2025-11-10', fechaSalida = '2025-11-15', idHabitacion = 22
WHERE cedulaCliente = 1005 AND idHabitacion = 30 AND fechaLlegada = '2025-12-01';

SELECT r.cedulaCliente, r.idHabitacion, r.fechaLlegada, r.fechaSalida
FROM Reserva r
ORDER BY r.cedulaCliente;

--Consulta de habitaciones y sus estados
SELECT h.idHabitacion, (c.precioCategoria * GREATEST(DATE_PART('day', r.fechaSalida - r.fechaLlegada), 1)) AS precioEstadia, 
CASE WHEN h.mantenimiento = TRUE THEN 'En mantenimiento' WHEN r.fechaLlegada <= CURRENT_DATE AND r.fechaSalida >= CURRENT_DATE THEN 'Ocupada' ELSE 'Disponible' END AS estadoActual
FROM Habitacion h
JOIN Categoria c ON h.idCategoria = c.idCategoria
LEFT JOIN Reserva r ON h.idHabitacion = r.idHabitacion
ORDER BY h.idHabitacion;

--Consulta y actualizacion de estados de habitaciones asignadas
SELECT h.idHabitacion, (c.precioCategoria * GREATEST(DATE_PART('day', r.fechaSalida - r.fechaLlegada), 1)) AS precioEstadia
FROM Habitacion h
JOIN Categoria c ON h.idCategoria = c.idCategoria
JOIN Reserva r ON h.idHabitacion = r.idHabitacion
WHERE h.mantenimiento = FALSE AND NOT (r.fechaLlegada <= CURRENT_DATE AND r.fechaSalida >= CURRENT_DATE)
ORDER BY h.idHabitacion;

DELETE FROM Reserva
WHERE idHabitacion = 11;

UPDATE Habitacion
SET mantenimiento = TRUE
WHERE idHabitacion = 11;

INSERT INTO Reserva(cedulaCliente, idHabitacion, fechaLlegada, fechaSalida)
VALUES (1001, 27, CURRENT_DATE, CURRENT_DATE + INTERVAL '3 days');

SELECT h.idHabitacion, (c.precioCategoria * GREATEST(DATE_PART('day', r.fechaSalida - r.fechaLlegada), 1)) AS precioEstadia, 
CASE WHEN h.mantenimiento = TRUE THEN 'En mantenimiento' WHEN r.fechaLlegada <= CURRENT_DATE AND r.fechaSalida >= CURRENT_DATE THEN 'Ocupada' ELSE 'Disponible' END AS estadoActual
FROM Habitacion h
JOIN Categoria c ON h.idCategoria = c.idCategoria
LEFT JOIN Reserva r ON h.idHabitacion = r.idHabitacion
ORDER BY h.idHabitacion;

--Consulta y registro de consumos adicionales de los huespedes
SELECT rs.cedulaCliente, rs.idHabitacion, rs.fechaLlegada, s.nombreServicio, s.precioServicio, rs.fechaUso
FROM Reserva_Servicio rs
JOIN Servicio s ON rs.idServicio = s.idServicio
ORDER BY rs.cedulaCliente, rs.idHabitacion, rs.fechaLlegada;

INSERT INTO Reserva_Servicio (idServicio, fechaLlegada, cedulaCliente, idHabitacion, fechaUso)
SELECT 3, r.fechaLlegada, r.cedulaCliente, r.idHabitacion, NOW()
FROM Reserva r
WHERE r.cedulaCliente = 1001 AND r.idHabitacion = 27
LIMIT 1;

SELECT rs.cedulaCliente, rs.idHabitacion, rs.fechaLlegada, s.nombreServicio, s.precioServicio, rs.fechaUso
FROM Reserva_Servicio rs
JOIN Servicio s ON rs.idServicio = s.idServicio
ORDER BY rs.cedulaCliente, rs.idHabitacion, rs.fechaLlegada;

--Consulta y actualizacion de empleados
SELECT e.cedulaEmpleado, e.primerNombre, e.segundoNombre, e.primerApellido, e.segundoApellido, e.calle, e.carrera, e.numero, e.complemento, te.telefonoEmpleado, ee.correoEmpleado, e.cargo, e.idArea
FROM Empleado e LEFT JOIN TelefonoEmpleado te ON e.cedulaEmpleado = te.cedulaEmpleado LEFT JOIN EmailEmpleado ee ON e.cedulaEmpleado = ee.cedulaEmpleado
ORDER BY e.cedulaEmpleado;

INSERT INTO Empleado(cedulaEmpleado, primerNombre, segundoNombre, primerApellido, segundoApellido, calle, carrera, numero, complemento, cargo, idArea)
VALUES (2016, 'Jose', 'Manuel', 'Pardo', NULL, '10', '4', '88', NULL, 'Recepcionista', 1);
INSERT INTO TelefonoEmpleado(cedulaEmpleado, telefonoEmpleado) VALUES (2016, '3102223344');
INSERT INTO EmailEmpleado(cedulaEmpleado, correoEmpleado) VALUES (2016, 'emp300@mail.com');

UPDATE Empleado
SET cargo = 'Supervisor'
WHERE cedulaEmpleado = 2001;
UPDATE TelefonoEmpleado
SET telefonoEmpleado = '3109998888'
WHERE cedulaEmpleado = 2001;
DELETE FROM Empleado
WHERE cedulaEmpleado = 2015;

SELECT e.cedulaEmpleado, e.primerNombre, e.segundoNombre, e.primerApellido, e.segundoApellido, e.calle, e.carrera, e.numero, e.complemento, te.telefonoEmpleado, ee.correoEmpleado, e.cargo, e.idArea
FROM Empleado e LEFT JOIN TelefonoEmpleado te ON e.cedulaEmpleado = te.cedulaEmpleado LEFT JOIN EmailEmpleado ee ON e.cedulaEmpleado = ee.cedulaEmpleado
ORDER BY e.cedulaEmpleado;

--Consulta y actualizacion de servicios
SELECT *
FROM Servicio;

INSERT INTO Servicio(idServicio, nombreServicio, detalleServicio, precioServicio)
VALUES (6, 'MiniBar', 'Consumo de bebidas', 45000);

UPDATE Servicio
SET precioServicio = 50000, detalleServicio = 'Consumo minibar premium'
WHERE idServicio = 6;

DELETE FROM Servicio
WHERE idServicio = 5;

SELECT *
FROM Servicio;

--Consultas para analisis
--Consumo total por reserva
SELECT r.cedulaCliente, r.idHabitacion, r.fechaLlegada, c.precioCategoria * GREATEST(DATE_PART('day', r.fechaSalida - r.fechaLlegada), 1) AS precioEstadia, COALESCE(SUM(s.precioServicio), 0) AS precioServicios, (c.precioCategoria * GREATEST(DATE_PART('day', r.fechaSalida - r.fechaLlegada), 1) + COALESCE(SUM(s.precioServicio), 0)) AS consumoTotal
FROM Reserva r
JOIN Habitacion h ON r.idHabitacion = h.idHabitacion
JOIN Categoria c ON h.idCategoria = c.idCategoria
LEFT JOIN Reserva_Servicio rs ON rs.cedulaCliente = r.cedulaCliente AND rs.idHabitacion = r.idHabitacion AND rs.fechaLlegada = r.fechaLlegada
LEFT JOIN Servicio s ON s.idServicio = rs.idServicio
GROUP BY r.cedulaCliente, r.idHabitacion, r.fechaLlegada, r.fechaSalida, c.precioCategoria
ORDER BY r.cedulaCliente, r.idHabitacion, r.fechaLlegada;

--Ocupacion por mes
SELECT EXTRACT(YEAR FROM fechaLlegada) AS año, EXTRACT(MONTH FROM fechaLlegada) AS mes, COUNT(*) AS total_reservas, COUNT(DISTINCT idHabitacion) AS habitaciones_ocupadas
FROM Reserva
GROUP BY año, mes
ORDER BY año, mes;

--Duracion promedio, minima y maxima de las reservas
SELECT AVG(fechaSalida - fechaLlegada) AS duracionPromedio, MIN(fechaSalida - fechaLlegada) AS duracionMinima, MAX(fechaSalida - fechaLlegada) AS duracionMaxima
FROM Reserva;

--Ingresos recibidos por servicios adicionales
SELECT s.nombreServicio, COUNT(rs.idServicio) AS vecesSolicitado, SUM(s.precioServicio) AS ingresosTotales, AVG(s.precioServicio) AS precioPromedio
FROM Servicio s
LEFT JOIN Reserva_Servicio rs ON s.idServicio = rs.idServicio
GROUP BY s.idServicio, s.nombreServicio;

--Clientes mas valiosos
SELECT c.cedulaCliente, c.primerNombre || ' ' || c.primerApellido AS nombreCompleto, COUNT(DISTINCT (r.cedulaCliente, r.idHabitacion, r.fechaLlegada)) AS totalReservas, COUNT(rs.idServicio) AS serviciosConsumidos, COALESCE(SUM(cat.precioCategoria * EXTRACT(DAY FROM (r.fechaSalida - r.fechaLlegada))), 0) + COALESCE(SUM(s.precioServicio), 0) AS ingresosTotalesRecibidos
FROM Cliente c
LEFT JOIN Reserva r ON c.cedulaCliente = r.cedulaCliente
LEFT JOIN Habitacion h ON r.idHabitacion = h.idHabitacion
LEFT JOIN Categoria cat ON h.idCategoria = cat.idCategoria
LEFT JOIN Reserva_Servicio rs ON r.cedulaCliente = rs.cedulaCliente AND r.idHabitacion = rs.idHabitacion AND r.fechaLlegada = rs.fechaLlegada
LEFT JOIN Servicio s ON rs.idServicio = s.idServicio
GROUP BY c.cedulaCliente, nombreCompleto
ORDER BY c.cedulaCliente;