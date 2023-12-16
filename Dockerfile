FROM openjdk:17-jre

WORKDIR /app

COPY build/libs/Task-Management-System-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]
