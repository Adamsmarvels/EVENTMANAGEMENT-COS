# ---- Stage 1: Build the app with Maven ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the Maven wrapper + pom first (better build caching)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B || true

# Copy the source and build the jar (skip tests for a fast deploy)
COPY src ./src
RUN chmod +x mvnw && ./mvnw clean package -DskipTests -B

# ---- Stage 2: Run the built jar on a small Java runtime ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
