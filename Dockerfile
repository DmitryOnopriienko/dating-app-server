FROM library/amazoncorretto:21.0.0

WORKDIR /app
ARG JAR_FILE=build/libs/dating-app-*.jar
COPY ${JAR_FILE} ./app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
