FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

CMD ["java", "-jar", "shopping-King-api/build/libs/shopping-King-api-0.0.1-SNAPSHOT.jar"]
