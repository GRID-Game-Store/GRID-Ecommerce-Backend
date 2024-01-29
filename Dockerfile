FROM openjdk:17-alpine

# Install Maven
RUN apk update
RUN apk add maven

COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

EXPOSE 8082
ENTRYPOINT ["java","-jar","/home/app/target/backend-0.0.1-SNAPSHOT.jar"]