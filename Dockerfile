# Stage 1: Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run stage
FROM eclipse-temurin:17-jre-focal
COPY --from=build /target/*.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java","-jar","/app.jar"]