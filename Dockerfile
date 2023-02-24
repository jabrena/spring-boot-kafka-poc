FROM openjdk:17.0.2-slim-bullseye
COPY target/spring-boot-kafka-poc-0.1.0-SNAPSHOT.jar spring-boot-kafka-poc.jar
ENTRYPOINT ["java", "-jar", "spring-boot-kafka-poc.jar"]