# Script para configurar la base de datos del Hotel
# Ejecutar como: .\setup_database.ps1

$PGHOST = "localhost"
$PGPORT = "5432"
$PGUSER = "postgres"
$PGDATABASE = "postgres"

Write-Host "=== Configuración de Base de Datos Hotel ===" -ForegroundColor Cyan
Write-Host ""
Write-Host "Este script creará la base de datos 'hotel' y ejecutará los scripts SQL"
Write-Host "Necesitarás la contraseña del usuario 'postgres'"
Write-Host ""

# Verificar si la base de datos 'hotel' existe
Write-Host "1. Verificando si existe la base de datos 'hotel'..." -ForegroundColor Yellow
$checkDB = "SELECT 1 FROM pg_database WHERE datname='hotel';"
$exists = psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -t -c $checkDB 2>$null

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error: No se pudo conectar a PostgreSQL. Verifica que el servidor esté corriendo." -ForegroundColor Red
    Write-Host "Comando: net start postgresql-x64-18" -ForegroundColor Yellow
    exit 1
}

if ($exists -match "1") {
    Write-Host "   ✓ La base de datos 'hotel' ya existe" -ForegroundColor Green
    $respuesta = Read-Host "¿Deseas recrearla? (s/n)"
    if ($respuesta -eq "s") {
        Write-Host "   Eliminando base de datos existente..." -ForegroundColor Yellow
        psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -c "DROP DATABASE IF EXISTS hotel;"
        Write-Host "   Creando base de datos 'hotel'..." -ForegroundColor Yellow
        psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -c "CREATE DATABASE hotel;"
    }
} else {
    Write-Host "   Creando base de datos 'hotel'..." -ForegroundColor Yellow
    psql -h $PGHOST -p $PGPORT -U $PGUSER -d $PGDATABASE -c "CREATE DATABASE hotel;"
    if ($LASTEXITCODE -eq 0) {
        Write-Host "   ✓ Base de datos 'hotel' creada" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "2. Ejecutando scripts SQL..." -ForegroundColor Yellow

$scriptsDir = "src\main\resources\scripts"

# Ejecutar DDL
Write-Host "   Ejecutando DDL-Hotel.SQL (estructura de tablas)..." -ForegroundColor Cyan
psql -h $PGHOST -p $PGPORT -U $PGUSER -d hotel -f "$scriptsDir\DDL_Hotel.SQL"
if ($LASTEXITCODE -eq 0) {
    Write-Host "   ✓ DDL ejecutado correctamente" -ForegroundColor Green
} else {
    Write-Host "   ✗ Error al ejecutar DDL" -ForegroundColor Red
    exit 1
}

# Ejecutar DML
Write-Host "   Ejecutando DML-Hotel.SQL (datos iniciales)..." -ForegroundColor Cyan
psql -h $PGHOST -p $PGPORT -U $PGUSER -d hotel -f "$scriptsDir\DML-Hotel.SQL"
if ($LASTEXITCODE -eq 0) {
    Write-Host "   ✓ DML ejecutado correctamente" -ForegroundColor Green
} else {
    Write-Host "   ✗ Error al ejecutar DML" -ForegroundColor Red
    exit 1
}

# Ejecutar DCL
Write-Host "   Ejecutando DCL-Hotel.SQL (roles y permisos)..." -ForegroundColor Cyan
psql -h $PGHOST -p $PGPORT -U $PGUSER -d hotel -f "$scriptsDir\DCL-Hotel.SQL"
if ($LASTEXITCODE -eq 0) {
    Write-Host "   ✓ DCL ejecutado correctamente" -ForegroundColor Green
} else {
    Write-Host "   ✗ Error al ejecutar DCL" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "=== Configuración completada ===" -ForegroundColor Green
Write-Host ""
Write-Host "Usuarios creados:" -ForegroundColor Cyan
Write-Host "  - user_recepcion (contraseña: pwd_recepcion)"
Write-Host "  - user_servicio (contraseña: pwd_servicio)"
Write-Host "  - user_administracion (contraseña: pwd_adm)"
Write-Host "  - db_admin (contraseña: adminHotel123)"
Write-Host ""
Write-Host "Puedes iniciar sesión en la aplicación con cualquiera de estos usuarios." -ForegroundColor Yellow
