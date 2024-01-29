FROM openjdk:17-alpine AS build

# Install Maven
RUN apk update
RUN apk add maven

WORKDIR /home/app

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17-alpine

COPY --from=build /home/app/target/grid-0.0.1-SNAPSHOT.jar /home/app/target/grid-0.0.1-SNAPSHOT.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/home/app/target/grid-0.0.1-SNAPSHOT.jar"]
