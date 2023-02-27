# Spring Boot Kafka POC

[![CI Builds](https://github.com/jabrena/spring-boot-kafka-poc/actions/workflows/build.yaml/badge.svg)](https://github.com/jabrena/spring-boot-kafka-poc/actions/workflows/build.yaml)

```
mvn clean package

docker compose up --build
docker compose logs kafka-example --follow
http://localhost:8080/swagger-ui/index.html
curl --json '{"message": "hello world"}' http://localhost:8080/api/v1/messages
```

# References

- https://kafka.apache.org/documentation/
- https://docs.spring.io/spring-kafka/reference/html/
- https://www.testcontainers.org/modules/kafka/
- https://springdoc.org/v2/

# Something to read for the future

- https://codersee.com/how-to-set-up-kafka-without-zookeeper-using-docker-compose/