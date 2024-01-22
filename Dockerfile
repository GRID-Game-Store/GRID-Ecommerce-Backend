# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /home/app
COPY . /home/app
RUN mvn clean install -U -N -f pom.xml

# Stage 2: Build the application
FROM openjdk:17-alpine

WORKDIR /home/app
COPY --from=builder /root/.m2 /root/.m2
COPY . /home/app

# Install Maven in the container
RUN apk add --no-cache maven

RUN mvn clean package -f pom.xml

EXPOSE 8082
ENTRYPOINT ["java","-jar","/home/app/backend/target/backend-0.0.1-SNAPSHOT.jar"]