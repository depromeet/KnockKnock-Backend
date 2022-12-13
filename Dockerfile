FROM openjdk:11.0.6-jre

EXPOSE 8080

COPY ./build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]