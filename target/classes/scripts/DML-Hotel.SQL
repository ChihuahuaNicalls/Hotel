INSERT INTO Area(idArea, nombreArea) VALUES
(1, 'Recepcion'), (2, 'Aseo'), (3, 'Restaurante'), (4, 'Cocina'), (5, 'Mantenimiento');

INSERT INTO Categoria(idCategoria, nombreCategoria, descCategoria, precioCategoria) VALUES
(1,'Suite','Habitacion de lujo con comodidades premium',500000), (2,'Premium','Habitacion amplia y elegante',350000),
(3,'Estandar','Habitacion con servicios basicos',250000), (4,'Economica','Habitacion sencilla y accesible',150000),
(5,'Familiar','Espacio grande para grupos o familias',450000);

INSERT INTO Cliente(cedulaCliente, primerNombre, segundoNombre, primerApellido, segundoApellido, calle, carrera, numero, complemento) VALUES
(1001,'Carlos','Andres','Lopez','Perez','Calle 10','Carrera 5','12-34',NULL),
(1002,'Maria','Jose','Gomez','Rios','Calle 22','Carrera 18','44-12','Apto 3'),
(1003,'Julian','Esteban','Morales','Quintero','Calle 4','Carrera 2','21-11',NULL),
(1004,'Luisa','Fernanda','Hernandez','Rodriguez','Calle 7','Carrera 15','09-51','Ed 2'),
(1005,'Diana','Patricia','Vargas','Mejia','Calle 33','Carrera 9','17-22',NULL),
(1006,'Felipe','Andres','Pardo','Soto','Calle 19','Carrera 11','45-78','Casa B'),
(1007,'Natalia','Andrea','Cruz','Molina','Calle 2','Carrera 3','89-90',NULL),
(1008,'Camilo','Jose','Rincon','Vera','Calle 40','Carrera 20','33-14','Apto 301'),
(1009,'Juan','Sebastian','Muñoz','Patiño','Calle 14','Carrera 8','65-14',NULL),
(1010,'Paula','Alejandra','Cobo','Perez','Calle 8','Carrera 12','99-77',NULL),
(1011,'Andres','Felipe','Rojas','Garcia','Calle 50','Carrera 21','12-54','Casa 2'),
(1012,'Karla','Paola','Zamora','Mora','Calle 18','Carrera 7','44-12',NULL),
(1013,'Oscar','Ivan','Santos','Cano','Calle 27','Carrera 4','21-88','Block A'),
(1014,'Sara','Lucia','Luna','Rios','Calle 29','Carrera 18','92-14',NULL),
(1015,'Emilio','Javier','Correa','Mena','Calle 13','Carrera 10','14-26',NULL),
(1016,'Daniela','Andrea','Castro','Lara','Calle 6','Carrera 1','41-22','Apto 2'),
(1017,'David','Enrique','Bermudez','Lopez','Calle 15','Carrera 17','33-20',NULL),
(1018,'Tatiana','Maria','Lozano','Beltran','Calle 5','Carrera 3','55-10',NULL),
(1019,'Kevin','Samuel','Perez','Mora','Calle 31','Carrera 9','90-44','Casa C'),
(1020,'Lorena','Paola','Ortiz','Salas','Calle 21','Carrera 6','20-11',NULL);

INSERT INTO TelefonoCliente(cedulaCliente, telefonoCliente) VALUES
(1001,'3001111111'), (1002,'3001111112'), (1003,'3001111113'),
(1004,'3001111114'), (1005,'3001111115'), (1006,'3001111116'),
(1007,'3001111117'), (1008,'3001111118'), (1009,'3001111119'),
(1010,'3001111120'), (1011,'3001111121'), (1012,'3001111122'),
(1013,'3001111123'), (1014,'3001111124'), (1015,'3001111125'),
(1016,'3001111126'), (1017,'3001111127'), (1018,'3001111128'),
(1019,'3001111129'), (1020,'3001111130');

INSERT INTO EmailCliente(cedulaCliente, correoCliente) VALUES
(1001,'carlos1@mail.com'), (1002,'maria2@mail.com'), (1003,'julian3@mail.com'),
(1004,'luisa4@mail.com'), (1005,'diana5@mail.com'), (1006,'felipe6@mail.com'),
(1007,'natalia7@mail.com'), (1008,'camilo8@mail.com'), (1009,'juan9@mail.com'),
(1010,'paula10@mail.com'), (1011,'andres11@mail.com'), (1012,'karla12@mail.com'),
(1013,'oscar13@mail.com'), (1014,'sara14@mail.com'), (1015,'emilio15@mail.com'),
(1016,'daniela16@mail.com'), (1017,'david17@mail.com'), (1018,'tatiana18@mail.com'),
(1019,'kevin19@mail.com'), (1020,'lorena20@mail.com');

INSERT INTO Empleado(cedulaEmpleado, primerNombre, segundoNombre, primerApellido, segundoApellido, calle, carrera, numero, complemento, cargo, idArea) VALUES
(2001,'Luis','Eduardo','Ramirez','Perez','Calle 10','Carrera 1','33-10',NULL,'Recepcionista',1),
(2002,'Ana','Maria','Gomez','Rivas','Calle 15','Carrera 7','21-44',NULL,'Recepcionista',1),
(2003,'Pablo','Andres','Herrera','Mora','Calle 3','Carrera 8','54-12',NULL,'Aseador',2),
(2004,'Jorge','Ivan','Diaz','Caro','Calle 20','Carrera 11','19-77','Casa 3','Aseador',2),
(2005,'Claudia','Patricia','Mejia','Vega','Calle 7','Carrera 14','11-10',NULL,'Mesera',3),
(2006,'Fernando','Jose','Lopez','Rivera','Calle 9','Carrera 9','22-30',NULL,'Mesero',3),
(2007,'Carmen','Rosa','Quintero','Lopez','Calle 30','Carrera 4','40-12',NULL,'Cocinera',4),
(2008,'Diego','Alejandro','Cardenas','Rojas','Calle 16','Carrera 2','45-20',NULL,'Cocinero',4),
(2009,'Marta','Lucia','Peralta','Santos','Calle 26','Carrera 18','12-22',NULL,'Tecnica',5),
(2010,'Raul','Esteban','Ortega','Mesa','Calle 2','Carrera 6','88-22',NULL,'Tecnico',5),
(2011,'Sonia','Pilar','Zapata','Vera','Calle 4','Carrera 10','50-14',NULL,'Recepcionista',1),
(2012,'Hugo','Ramon','Toro','Silva','Calle 18','Carrera 1','10-33',NULL,'Aseador',2),
(2013,'Viviana','Ruth','Linares','Valencia','Calle 11','Carrera 13','75-99',NULL,'Mesera',3),
(2014,'Camila','Andrea','Muñoz','Garcia','Calle 6','Carrera 19','41-21',NULL,'Cocinera',4),
(2015,'Sebastian','Enrique','Arango','Cruz','Calle 10','Carrera 20','22-19',NULL,'Tecnico',5);

INSERT INTO TelefonoEmpleado(cedulaEmpleado, telefonoEmpleado) VALUES
(2001,'3101111111'), (2002,'3101111112'), (2003,'3101111113'),
(2004,'3101111114'), (2005,'3101111115'), (2006,'3101111116'),
(2007,'3101111117'), (2008,'3101111118'), (2009,'3101111119'),
(2010,'3101111120'), (2011,'3101111121'), (2012,'3101111122'),
(2013,'3101111123'), (2014,'3101111124'), (2015,'3101111125');

INSERT INTO EmailEmpleado(cedulaEmpleado, correoEmpleado) VALUES
(2001,'luis@mail.com'), (2002,'ana@mail.com'), (2003,'pablo@mail.com'),
(2004,'jorge@mail.com'), (2005,'claudia@mail.com'), (2006,'fernando@mail.com'),
(2007,'carmen@mail.com'), (2008,'diego@mail.com'), (2009,'marta@mail.com'),
(2010,'raul@mail.com'), (2011,'sonia@mail.com'), (2012,'hugo@mail.com'),
(2013,'viviana@mail.com'), (2014,'camila@mail.com'), (2015,'sebastian@mail.com');

INSERT INTO Habitacion(idHabitacion, idCategoria, numeroHabitacion, piso, descripcion) VALUES
(1,4,101,1,'Incluye buenas vistas'), (2,4,102,1,'Cerca del ascensor'), (3,3,103,1,'Ambiente tranquilo'), (4,3,104,1,'Espacio iluminado'),
(5,3,105,1,'Amplio espacio'), (6,3,106,1,'Decoracion moderna'), (7,3,201,2,'Incluye buenas vistas'), (8,3,202,2,'Cerca del ascensor'),
(9,3,203,2,'Balcon pequeño'), (10,3,204,2,'Ideal para parejas'), (11,3,205,2,'Espacio acogedor'), (12,3,206,2,'Iluminacion calida'),
(13,2,301,3,'Vista panoramica'), (14,2,302,3,'Cerca del ascensor'), (15,2,303,3,'Decoracion moderna'), (16,2,304,3,'Espacio silencioso'),
(17,2,305,3,'Amplio espacio'), (18,2,306,3,'Recien renovada'), (19,5,401,4,'Incluye buenas vistas'), (20,5,402,4,'Cerca del ascensor'), 
(21,5,403,4,'Decoracion elegante'), (22,5,404,4,'Balcon amplio'), (23,5,405,4,'Ambiente luminoso'), (24,5,406,4,'Espacio renovado'), 
(25,1,501,5,'Lujo y comodidad'), (26,1,502,5,'Vista espectacular'), (27,1,503,5,'Decoracion sofisticada'), (28,1,504,5,'Espacio premium'),
(29,1,505,5,'Balcon con vista privilegiada'), (30,1,506,5,'Iluminacion natural intensa');

INSERT INTO Servicio(idServicio, nombreServicio, detalleServicio, precioServicio) VALUES
(1,'Spa','Acceso al circuito de spa',80000), (2,'Desayuno','Desayuno buffet',30000), (3,'Almuerzo','Almuerzo menu ejecutivo',45000),
(4,'Lavanderia','Servicio de lavado de ropa',25000), (5,'Transporte','Traslado aeropuerto-hotel',60000);

INSERT INTO Reserva(cedulaCliente, idHabitacion, fechaLlegada, fechaSalida) VALUES
(1001,1,'2025-01-02','2025-01-05'), (1002,2,'2025-01-10','2025-01-12'), (1003,3,'2025-02-01','2025-02-03'), (1004,4,'2025-02-15','2025-02-20'),
(1005,5,'2025-03-05','2025-03-08'), (1006,7,'2025-03-15','2025-03-18'), (1007,8,'2025-04-01','2025-04-04'), (1008,9,'2025-04-10','2025-04-12'),
(1009,10,'2025-05-03','2025-05-06'), (1010,11,'2025-05-15','2025-05-18'), (1011,13,'2025-06-01','2025-06-04'), (1012,14,'2025-06-10','2025-06-12'),
(1013,15,'2025-07-02','2025-07-05'), (1014,16,'2025-07-18','2025-07-20'), (1015,17,'2025-08-05','2025-08-09'), (1016,18,'2025-08-20','2025-08-22');

INSERT INTO Reserva_Servicio(idServicio, fechaLlegada, cedulaCliente, idHabitacion, fechaUso) VALUES
(1, '2025-01-02', 1001, 1, '2025-01-03'), (2, '2025-01-10', 1002, 2, '2025-01-11'), (3, '2025-02-01', 1003, 3, '2025-02-02'),
(4, '2025-02-15', 1004, 4, '2025-02-16'), (5, '2025-03-05', 1005, 5, '2025-03-06'), (1, '2025-03-15', 1006, 7, '2025-03-16'),
(2, '2025-04-01', 1007, 8, '2025-04-02'), (3, '2025-04-10', 1008, 9, '2025-04-11'), (4, '2025-05-03', 1009, 10, '2025-05-04'),
(5, '2025-05-15', 1010, 11, '2025-05-16'), (2, '2025-06-01', 1011, 13, '2025-06-02'), (1, '2025-06-10', 1012, 14, '2025-06-11'),
(3, '2025-07-02', 1013, 15, '2025-07-03'), (4, '2025-07-18', 1014, 16, '2025-07-19'), (5, '2025-08-05', 1015, 17, '2025-08-06'),
(1, '2025-08-20', 1016, 18, '2025-08-21');

INSERT INTO Atiende(cedulaCliente, cedulaEmpleado, idHabitacion, idServicio, fechaLlegada, fechaUso) VALUES
(1001, 2009, 1, 1, '2025-01-02', '2025-01-03'), (1002, 2005, 2, 2, '2025-01-10', '2025-01-11'), (1003, 2005, 3, 3, '2025-02-01', '2025-02-02'),
(1004, 2003, 4, 4, '2025-02-15', '2025-02-16'), (1005, 2001, 5, 5, '2025-03-05', '2025-03-06'), (1006, 2009, 7, 1, '2025-03-15', '2025-03-16'),
(1007, 2005, 8, 2, '2025-04-01', '2025-04-02'), (1008, 2005, 9, 3, '2025-04-10', '2025-04-11'), (1009, 2003, 10, 4, '2025-05-03', '2025-05-04'),
(1010, 2001, 11, 5, '2025-05-15', '2025-05-16'), (1011, 2005, 13, 2, '2025-06-01', '2025-06-02'), (1012, 2009, 14, 1, '2025-06-10', '2025-06-11'),
(1013, 2005, 15, 3, '2025-07-02', '2025-07-03'), (1014, 2003, 16, 4, '2025-07-18', '2025-07-19'), (1015, 2001, 17, 5, '2025-08-05', '2025-08-06'),
(1016, 2009, 18, 1, '2025-08-20', '2025-08-21');