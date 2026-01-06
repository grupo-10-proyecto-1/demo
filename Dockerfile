# Etapa de construcción
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Descargar dependencias (cache layer)
RUN ./mvnw dependency:go-offline
COPY src ./src
# Empaquetar sin correr tests para agilizar
RUN ./mvnw package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]