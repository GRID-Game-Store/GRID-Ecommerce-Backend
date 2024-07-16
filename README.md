# Project GRID

This is backend part of educational GRID project. Instruction describes how to clone and run the project on your local
machine.

## Getting Started

These instructions will help you get a copy of the project and running it on your local machine for development and
testing purposes.

## How to Run

This application requires
pre-installed [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or
higher. [See more.](https://www.oracle.com/java/technologies/downloads/#jdk19-windows)

* Clone this repository

```
git clone https://github.com/GRID-Game-Store/backend
```

* Make sure you are using JDK 17 and Maven
* You can build the project and run the tests by running ```mvn clean package```
  or
  ```mvn install / mvn clean install```

### Run Spring Boot app using Maven:

Now you can launch the server 8082.

* run Spring Boot app using Maven:
  ```mvn spring-boot:run```
* [optional] Run Spring Boot app with``` java -jar command
  java -jar target/backend-0.0.1-SNAPSHOT.jar```

### Run Spring Boot app using Docker:
To run Spring Boot app using Docker:
* Run the Docker Compose file inside backend folder:
  ```docker-compose -f docker-compose.yml up```

### Running Keycloak in docker

Keycloak is used for authentication and can be started using Docker Compose.
The provided docker-compose.yml file includes a Keycloak service and a PostgreSQL database for Keycloak.

To run Keycloak along with the backend:

* Run the Docker Compose file:
  ```docker-compose -f docker-compose.yml up```

This will start Keycloak on port 8084.
It is recommended to start Keycloak before the backend to ensure that the authentication service is available.

[Link to documentation](https://github.com/GRID-Game-Store/documentation/tree/main/backend)

## API Documentation

The API documentation is available via Swagger UI. After starting the application, you can access it at:
```http://localhost:8084/swagger```
This interface provides a comprehensive list of all available endpoints and allows you to test them directly from your browser.

## GRID Demo Video

Check out our demo video showcasing the main features of the GRID project:

### Main Page

https://github.com/user-attachments/assets/540fdc0d-b89e-4ab5-b09a-98554d3e0f59

### AI Chat Consultant

https://github.com/user-attachments/assets/48e87a2d-fe04-44a7-b9c8-6060a6840901

### Authentication, Authorization 

https://github.com/user-attachments/assets/759e81ef-abc9-462f-bc86-2146ac7ed2e6

### Payment Abilities

https://github.com/user-attachments/assets/8648e341-3ea2-4f1c-968f-13e65f6395f2


The demo includes:
- Overview of the main page
- AI-powered chat consultant using Vertext Gemini
- Registration and authorization process with Keycloak
- Payment integration with Stripe and PayPal
- Admin panel walkthrough

## Features

- **AI Chat Consultant**: Powered by Vertext Gemini, providing intelligent customer support.
- **Secure Authentication**: Implemented using Keycloak for robust user management.
- **Multiple Payment Options**: Integrated with Stripe and PayPal for flexible payment processing.
- **Admin Panel**: Comprehensive admin interface for easy management of the platform with a help of Astro.js and Spring Boot

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring Boot](https://spring.io/projects/spring-boot) - Server Framework
* [JUnit](https://junit.org/junit5/) - Testing Framework
* [Keycloak](https://www.keycloak.org/) - Identity and Access Management
* [Swagger](https://swagger.io/) - API Documentation
* [Docker](https://www.docker.com/) - Containerization Platform

## Authors:

* [SEM24](https://github.com/SEM24)

[Link to backend documentation](https://github.com/GRID-Game-Store/documentation/tree/main/backend)

