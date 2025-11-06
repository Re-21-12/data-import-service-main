# Multi-stage build: compilar con Maven, ejecutar con runtime ligero
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace

# Instalar Maven en el contenedor base con JDK21 (evita depender de un tag maven:... que no exista)
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
	&& apt-get install -y --no-install-recommends maven ca-certificates \
	&& rm -rf /var/lib/apt/lists/*

# Copiar pom y fuente, usar cache de dependencias
COPY pom.xml ./

# Descargar dependencias
RUN mvn -B dependency:go-offline

COPY src ./src

# Build (skip tests para acelerar; quitar -DskipTests en CI si quieres tests)
RUN mvn -B -Dmaven.test.skip=true package

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar jar construido
COPY --from=build /workspace/target/*.jar app.jar

# Puerto por defecto que expone Spring Boot
EXPOSE 8080

# Instalar herramientas necesarias para esperar a SQL Server (sqlcmd)
USER root
RUN apt-get update \
	&& apt-get install -y --no-install-recommends curl apt-transport-https gnupg ca-certificates lsb-release \
	&& curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add - \
	&& curl https://packages.microsoft.com/config/ubuntu/22.04/prod.list > /etc/apt/sources.list.d/mssql-release.list \
	&& apt-get update \
	&& ACCEPT_EULA=Y apt-get install -y --no-install-recommends msodbcsql17 unixodbc-dev mssql-tools \
	&& rm -rf /var/lib/apt/lists/*

# Copiar script de espera y dar permisos
COPY scripts/wait-for-sql.sh /app/wait-for-sql.sh
RUN chmod +x /app/wait-for-sql.sh

ENTRYPOINT ["/app/wait-for-sql.sh"]
