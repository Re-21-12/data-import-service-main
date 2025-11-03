# Multi-stage build: compilar con Maven, ejecutar con runtime ligero
FROM maven:3.10.1-jdk-21 AS build
WORKDIR /workspace

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
