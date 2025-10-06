FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY target/*.jar /app/app.jar
ENTRYPOINT  ["java","-jar","app.jar"]
