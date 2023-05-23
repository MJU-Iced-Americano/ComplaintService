FROM openjdk:17
#ARG JAR_FILE=complaint/build/libs/*.jar
COPY complaint/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]