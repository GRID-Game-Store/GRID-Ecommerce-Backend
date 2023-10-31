FROM openjdk:17
EXPOSE 8082
COPY target/grid-0.0.1-SNAPSHOT.jar grid-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/grid-0.0.1-SNAPSHOT.jar"]