FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/microservice01-0.0.1-SNAPSHOT.jar app.jar

ENV EUREKA_URL null
ENV BROKER_URL null
ENV CONFIG_SERVER null
ENTRYPOINT ["java","-jar","/app.jar"]