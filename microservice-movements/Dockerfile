# Etapa 1: Construcción de la aplicación
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copiamos el código fuente y lo construimos
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen mínima para ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos solo el JAR generado desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto de la aplicación
EXPOSE 8082

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
