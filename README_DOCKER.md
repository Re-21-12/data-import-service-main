# Docker — data-import-service

Instrucciones rápidas para build y ejecución con Docker (PowerShell en Windows):

1. Build local con Maven (si quieres generar jar localmente):

   ./mvnw -B -DskipTests package

2. Construir la imagen Docker:

   docker build -t data-import-service:latest .

3. Ejecutar la imagen:

   docker run --rm -p 8080:8080 --name data-import-service data-import-service:latest

4. Usar docker-compose (levanta la app, y opcionalmente una DB si la descomentas en `docker-compose.yml`):

   docker compose up --build

Notas:

- El `Dockerfile` usa una build multi-stage. La imagen final contiene sólo el runtime Java.
- Actualmente la app sólo tiene configurado `spring.application.name` en `application.properties` y no hay URL de datasource por defecto; Spring Boot usará H2 en memoria si no configuras `spring.datasource.*`.
- Para usar SQL Server en `docker-compose.yml` descomenta la sección `db` y ajusta la contraseña y la `SPRING_DATASOURCE_URL`.

## Swagger / OpenAPI

Este proyecto ahora incluye Springdoc OpenAPI y expone Swagger UI en:

http://localhost:8080/swagger-ui/index.html

Los endpoints que aceptan `MultipartFile` (como los del controlador `ImportController`) aparecerán en la UI con un input de tipo "file" para probar `multipart/form-data` directamente desde el navegador.

## Usando docker compose

1. Levanta los servicios (construye la imagen de la app y levanta SQL Server):

   docker compose up --build

2. Espera a que el contenedor `sqlserver` esté listo (la primera inicialización puede tardar). Luego abre Swagger UI en la URL anterior y prueba endpoints como `POST /import/equipo/csv` usando "Choose File".

Variables importantes:

- `SPRING_DATASOURCE_URL` se inyecta automáticamente al servicio `app` en `docker-compose.yml` apuntando a `sqlserver`.
- Usuario: `sa` / Contraseña: `YourStrong!Passw0rd` (ajusta en `docker-compose.yml` si lo deseas).
