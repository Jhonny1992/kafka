FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/microservice05-0.0.1-SNAPSHOT.jar app.jar
COPY example4.zip /tmp/example4.zip
ENV EUREKA_URL null
ENV BROKER_URL null
ENV RUTA_ZIP null
ENTRYPOINT ["java","-jar","/app.jar"]