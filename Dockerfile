FROM amazoncorretto:17

WORKDIR /app

COPY build/libs/votacao-0.0.1-SNAPSHOT-plain.jar ./app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]