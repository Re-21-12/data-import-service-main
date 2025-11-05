#!/usr/bin/env bash
set -e

# Valores por defecto (si no se pasan como env vars)
SPRING_DATASOURCE_URL="${SPRING_DATASOURCE_URL:-jdbc:sqlserver://sqlserver:1433;databaseName=Tablero_DB;trustServerCertificate=true;}"
SPRING_DATASOURCE_USERNAME="${SPRING_DATASOURCE_USERNAME:-sa}"
SPRING_DATASOURCE_PASSWORD="${SPRING_DATASOURCE_PASSWORD:-YourStrong!Passw0rd}"

# Extraer host, puerto y databaseName desde SPRING_DATASOURCE_URL
HOST=$(echo "$SPRING_DATASOURCE_URL" | sed -n 's#jdbc:sqlserver://\([^:;]*\).*#\1#p')
PORT=$(echo "$SPRING_DATASOURCE_URL" | sed -n 's#jdbc:sqlserver://[^:;]*:\([0-9]*\).*#\1#p')
DB=$(echo "$SPRING_DATASOURCE_URL" | sed -n 's#.*databaseName=\([^;]*\).*#\1#p')

HOST=${HOST:-sqlserver}
PORT=${PORT:-1433}
DB=${DB:-Tablero_DB}

export PATH="/opt/mssql-tools/bin:$PATH"

echo "Esperando a que SQL Server acepte conexiones en $HOST:$PORT..."
# Esperar hasta que sqlcmd responda
until /opt/mssql-tools/bin/sqlcmd -S "$HOST,$PORT" -U "$SPRING_DATASOURCE_USERNAME" -P "$SPRING_DATASOURCE_PASSWORD" -d master -Q "SELECT 1" >/dev/null 2>&1; do
  printf '.'
  sleep 1
done

echo "\nConexión a SQL Server establecida. Comprobando/creando base de datos '$DB'..."
# Crear la base de datos si no existe (ejecutar en master explícitamente)
/opt/mssql-tools/bin/sqlcmd -S "$HOST,$PORT" -U "$SPRING_DATASOURCE_USERNAME" -P "$SPRING_DATASOURCE_PASSWORD" -d master -Q "IF DB_ID(N'$DB') IS NULL CREATE DATABASE [$DB];"

echo "Base de datos verificada/creada. Arrancando la aplicación..."
exec java -jar /app/app.jar
