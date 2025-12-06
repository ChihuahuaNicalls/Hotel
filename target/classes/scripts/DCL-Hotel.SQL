--1. Roles de apoyo
CREATE ROLE lectura_completa;
CREATE ROLE escritura_completa;

--2. Roles operativos por área
CREATE ROLE recepcion;
CREATE ROLE personal_servicio;
CREATE ROLE administracion;

--3. Rol de administración de BD
CREATE ROLE db_admin LOGIN PASSWORD 'adminHotel123';
ALTER ROLE db_admin CREATEDB CREATEROLE;

--4. Jerarquía de permisos 
GRANT lectura_completa TO db_admin;
GRANT escritura_completa TO db_admin;

--5. Usuarios de ejemplo
CREATE ROLE user_recepcion LOGIN PASSWORD 'pwd_recepcion';
CREATE ROLE user_servicio LOGIN PASSWORD 'pwd_servicio';
CREATE ROLE user_administracion LOGIN PASSWORD 'pwd_adm';

GRANT recepcion TO user_recepcion;
GRANT personal_servicio TO user_servicio;
GRANT administracion TO user_administracion;

--6. Permisos al esquema public para todos los roles operativos
GRANT USAGE ON SCHEMA public TO recepcion;
GRANT USAGE ON SCHEMA public TO personal_servicio;
GRANT USAGE ON SCHEMA public TO administracion;

--7. Permisos específicos por área (según enunciado)
--Gestión completa de clientes y sus datos de contacto
GRANT SELECT, INSERT, UPDATE ON Cliente TO recepcion;
GRANT SELECT, INSERT, UPDATE, DELETE ON TelefonoCliente TO recepcion;
GRANT SELECT, INSERT, UPDATE, DELETE ON EmailCliente TO recepcion;

--Gestión completa de reservas
GRANT SELECT, INSERT, UPDATE ON Reserva TO recepcion;

--Consulta de habitaciones (solo lectura para verificar disponibilidad)
GRANT SELECT ON Habitacion TO recepcion;

--Consulta de categorías de habitaciones
GRANT SELECT ON Categoria TO recepcion;

--Consulta y actualización de habitaciones
GRANT SELECT, UPDATE ON Habitacion TO personal_servicio;

--Consulta de reservas para identificar huéspedes
GRANT SELECT ON Reserva TO personal_servicio;

--Consulta de clientes para verificar información
GRANT SELECT ON Cliente TO personal_servicio;

--Permisos para ver teléfonos y emails de clientes (necesarios para la vista de Clientes)
GRANT SELECT ON TelefonoCliente TO personal_servicio;
GRANT SELECT ON EmailCliente TO personal_servicio;

--Registro de servicios consumidos por huéspedes
GRANT SELECT, INSERT ON Reserva_Servicio TO personal_servicio;

--Consulta de servicios disponibles
GRANT SELECT ON Servicio TO personal_servicio;

--Consulta de áreas para servicios
GRANT SELECT ON Area TO personal_servicio;

--Consulta y registro de asignaciones (empleado atiende habitación)
GRANT SELECT, INSERT ON Atiende TO personal_servicio;

--Consulta de categorías (necesaria para calcular precios en disponibilidad)
GRANT SELECT ON Categoria TO personal_servicio;


--Gestión completa de empleados y sus datos de contacto
GRANT SELECT, INSERT, UPDATE, DELETE ON Empleado TO administracion;
GRANT SELECT, INSERT, UPDATE, DELETE ON TelefonoEmpleado TO administracion;
GRANT SELECT, INSERT, UPDATE, DELETE ON EmailEmpleado TO administracion;

--Gestión completa de servicios
GRANT SELECT, INSERT, UPDATE, DELETE ON Servicio TO administracion;

--Gestión de áreas
GRANT SELECT, INSERT, UPDATE, DELETE ON Area TO administracion;

--Gestión de categorías de habitaciones
GRANT SELECT, INSERT, UPDATE, DELETE ON Categoria TO administracion;

--Gestión de habitaciones
GRANT SELECT, INSERT, UPDATE, DELETE ON Habitacion TO administracion;

--Acceso analítico: consultas de lectura sobre datos operativos
GRANT SELECT ON Cliente TO administracion;
GRANT SELECT ON TelefonoCliente TO administracion;
GRANT SELECT ON EmailCliente TO administracion;
GRANT SELECT ON Reserva TO administracion;
GRANT SELECT ON Reserva_Servicio TO administracion;
GRANT SELECT ON Atiende TO administracion;

--8. Privilegios por defecto adicionales
ALTER DEFAULT PRIVILEGES FOR ROLE CURRENT_USER IN SCHEMA public
	GRANT SELECT ON TABLES TO lectura_completa;
ALTER DEFAULT PRIVILEGES FOR ROLE CURRENT_USER IN SCHEMA public
	GRANT INSERT, UPDATE, DELETE ON TABLES TO escritura_completa;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
	REVOKE ALL ON TABLES FROM lectura_completa;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
	REVOKE ALL ON SEQUENCES FROM lectura_completa;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
	REVOKE ALL ON FUNCTIONS FROM lectura_completa;

ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
	REVOKE ALL ON TABLES FROM escritura_completa;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
	REVOKE ALL ON SEQUENCES FROM escritura_completa;
ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public
	REVOKE ALL ON FUNCTIONS FROM escritura_completa;

--9. Permitir acceso al esquema public
GRANT USAGE ON SCHEMA public TO recepcion, personal_servicio, administracion;

--10. Revocar privilegios amplios previos
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM PUBLIC;
REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT USAGE ON SCHEMA public TO recepcion, personal_servicio, administracion;