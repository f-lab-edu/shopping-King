# Stage 1: Build the JAR file
FROM gradle:8.7-jdk17 AS builder

WORKDIR /app

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY shopping-King-api/src/ src/

RUN ./gradlew clean build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/shopping-King-api-0.0.1-SNAPSHOT.jar /app/shopping-King-api-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/app/shopping-King-api-0.0.1-SNAPSHOT.jar"]
