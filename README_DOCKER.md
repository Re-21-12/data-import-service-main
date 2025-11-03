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
