FROM openjdk:17
#ARG JAR_FILE=complaint/build/libs/*.jar
COPY ./build/libs/complaint-0.0.1-SNAPSHOT.jar app.jar
#COPY ./build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]