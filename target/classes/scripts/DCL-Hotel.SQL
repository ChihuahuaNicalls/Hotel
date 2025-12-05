-- Roles y privilegios para la base de datos del Hotel

-- 1) Roles de apoyo (para concesiones reutilizables)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'lectura_completa') THEN
        CREATE ROLE lectura_completa;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'escritura_completa') THEN
        CREATE ROLE escritura_completa;
    END IF;
END
$$;

-- 2) Roles operativos por área (grupos, sin LOGIN)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'recepcion') THEN
        CREATE ROLE recepcion;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'personal_servicio') THEN
        CREATE ROLE personal_servicio;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'administracion') THEN
        CREATE ROLE administracion;
    END IF;
END
$$;

-- 3) Rol de administración de BD (con LOGIN para tareas administrativas)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'db_admin') THEN
        CREATE ROLE db_admin LOGIN PASSWORD 'adminHotel123';
    END IF;
END
$$;
ALTER ROLE db_admin CREATEDB CREATEROLE;

-- 4) Jerarquía de permisos 
GRANT lectura_completa TO db_admin;
GRANT escritura_completa TO db_admin;

-- 5) Usuarios de ejemplo (LOGIN)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'user_recepcion') THEN
        CREATE ROLE user_recepcion LOGIN PASSWORD 'pwd_recepcion';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'user_servicio') THEN
        CREATE ROLE user_servicio LOGIN PASSWORD 'pwd_servicio';
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'user_administracion') THEN
        CREATE ROLE user_administracion LOGIN PASSWORD 'pwd_adm';
    END IF;
END
$$;

GRANT recepcion TO user_recepcion;
GRANT personal_servicio TO user_servicio;
GRANT administracion TO user_administracion;

-- 6) Permisos al esquema public para todos los roles operativos
GRANT USAGE ON SCHEMA public TO recepcion;
GRANT USAGE ON SCHEMA public TO personal_servicio;
GRANT USAGE ON SCHEMA public TO administracion;

-- 7) Permisos específicos por área (según enunciado)

-- ====================================================================================
-- RECEPCIÓN
-- ====================================================================================
-- - Gestiona información de clientes y reservas (insertar, actualizar, consultar).
-- - Consulta disponibilidad y estado de habitaciones.

-- Gestión completa de clientes y sus datos de contacto
GRANT SELECT, INSERT, UPDATE ON Cliente TO recepcion;
GRANT SELECT, INSERT, UPDATE ON TelefonoCliente TO recepcion;
GRANT SELECT, INSERT, UPDATE ON EmailCliente TO recepcion;

-- Gestión completa de reservas
GRANT SELECT, INSERT, UPDATE ON Reserva TO recepcion;

-- Consulta de habitaciones (solo lectura para verificar disponibilidad)
GRANT SELECT ON Habitacion TO recepcion;

-- Consulta de categorías de habitaciones
GRANT SELECT ON Categoria TO recepcion;



-- ====================================================================================
-- PERSONAL DE SERVICIO
-- ====================================================================================
-- - Consulta y actualiza el estado de las habitaciones asignadas.
-- - Registra consumos adicionales de los huéspedes.

-- Consulta y actualización de habitaciones
GRANT SELECT, UPDATE ON Habitacion TO personal_servicio;

-- Consulta de reservas para identificar huéspedes
GRANT SELECT ON Reserva TO personal_servicio;

-- Consulta de clientes para verificar información
GRANT SELECT ON Cliente TO personal_servicio;

-- Registro de servicios consumidos por huéspedes
GRANT SELECT, INSERT ON Reserva_Servicio TO personal_servicio;

-- Consulta de servicios disponibles
GRANT SELECT ON Servicio TO personal_servicio;

-- Consulta de áreas para servicios
GRANT SELECT ON Area TO personal_servicio;

-- Consulta y registro de asignaciones (empleado atiende habitación)
GRANT SELECT, INSERT ON Atiende TO personal_servicio;



-- ====================================================================================
-- ADMINISTRACIÓN
-- ====================================================================================
-- - Gestiona información de empleados (insertar, actualizar, eliminar, consultar).
-- - Administra información de servicios.
-- - Acceso a consultas generales para análisis.

-- Gestión completa de empleados y sus datos de contacto
GRANT SELECT, INSERT, UPDATE, DELETE ON Empleado TO administracion;
GRANT SELECT, INSERT, UPDATE, DELETE ON TelefonoEmpleado TO administracion;
GRANT SELECT, INSERT, UPDATE, DELETE ON EmailEmpleado TO administracion;

-- Gestión completa de servicios
GRANT SELECT, INSERT, UPDATE, DELETE ON Servicio TO administracion;

-- Gestión de áreas
GRANT SELECT, INSERT, UPDATE, DELETE ON Area TO administracion;

-- Gestión de categorías de habitaciones
GRANT SELECT, INSERT, UPDATE, DELETE ON Categoria TO administracion;

-- Gestión de habitaciones
GRANT SELECT, INSERT, UPDATE, DELETE ON Habitacion TO administracion;

-- Acceso analítico: consultas de lectura sobre datos operativos
GRANT SELECT ON Cliente TO administracion;
GRANT SELECT ON TelefonoCliente TO administracion;
GRANT SELECT ON EmailCliente TO administracion;
GRANT SELECT ON Reserva TO administracion;
GRANT SELECT ON Reserva_Servicio TO administracion;
GRANT SELECT ON Atiende TO administracion;

-- 8) Privilegios por defecto adicionales (objetos futuros creados por el propietario)
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

-- 9) Permitir acceso al esquema public
GRANT USAGE ON SCHEMA public TO recepcion, personal_servicio, administracion;

-- 10) Revocar privilegios amplios previos
REVOKE ALL PRIVILEGES ON ALL TABLES IN SCHEMA public FROM PUBLIC;
REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT USAGE ON SCHEMA public TO recepcion, personal_servicio, administracion;