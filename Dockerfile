FROM openjdk:17
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
COPY ./build/libs/complaint-0.0.1-SNAPSHOT app.jar
ENTRYPOINT ["java","-jar","/app.jar"]