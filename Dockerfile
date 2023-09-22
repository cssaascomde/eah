FROM amazoncorretto:17.0.7-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
CMD java -jar -Dspring.profiles.active=prod app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]