FROM eclipse-temurin:21
ARG JAR_FILE=./target/hackacode-3-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} hackacode-3-backend.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hackacode-3-backend.jar"]