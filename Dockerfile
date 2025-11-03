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
COPY src ./src

# Build (skip tests para acelerar; quitar -DskipTests en CI si quieres tests)
RUN mvn -B -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiar jar construido
COPY --from=build /workspace/target/*.jar app.jar

# Puerto por defecto que expone Spring Boot
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
