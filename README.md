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

[Link to documentation](https://github.com/GRID-Game-Store/documentation/blob/main/backend/docker.md)

# Game Controller

**CRUD operations for Game entities.**

This controller provides endpoints for performing CRUD operations on Game entities. It supports operations such as retrieving all games, filtering by genre, special offers, popularity, random games, and searching by title.

## Endpoints

### Get all games

- **Endpoint:** `/api/v1/games`
- **Method:** `GET`
- **Summary:** Get all games with optional pagination, sorting, and title filtering.
- **Parameters:**
  - `page` (optional, default: 0): Page number for pagination.
  - `size` (optional, default: 5, min: 1, max: Integer.MAX_VALUE): Number of items per page.
  - `title` (optional): Filter games by title.
  - `sort` (optional, default: "id,desc"): Sorting criteria.

### Get games by genre

- **Endpoint:** `/api/v1/games/genre`
- **Method:** `GET`
- **Summary:** Get games filtered by genre.
- **Parameters:**
  - `genre` (required): Genre to filter by.
  - `qty` (optional, default: 5, min: 1, max: Integer.MAX_VALUE): Number of games to retrieve.

### Get games by special offer

- **Endpoint:** `/api/v1/games/offers`
- **Method:** `GET`
- **Summary:** Get games with special offers.
- **Parameters:**
  - `query` (required): Special offer query.
  - `qty` (optional, default: 5, min: 1, max: Integer.MAX_VALUE): Number of games to retrieve.

### Get most popular games

- **Endpoint:** `/api/v1/games/popular`
- **Method:** `GET`
- **Summary:** Get the most popular games.
- **Parameters:**
  - `qty` (optional, default: 5, min: 1, max: Integer.MAX_VALUE): Number of games to retrieve.

### Get n-number of random games

- **Endpoint:** `/api/v1/games/random`
- **Method:** `GET`
- **Summary:** Get a specified number of random games.
- **Parameters:**
  - `qty` (optional, default: 20, min: 1, max: Integer.MAX_VALUE): Number of games to retrieve.

### Get game by title

- **Endpoint:** `/api/v1/games/search`
- **Method:** `GET`
- **Summary:** Search for games by title symbol by symbol.
- **Parameters:**
  - `title` (required): Title to search for.
  - `qty` (optional, default: 20, min: 1, max: Integer.MAX_VALUE): Number of games to retrieve.

### Get game by id

- **Endpoint:** `/api/v1/games/{game-id}`
- **Method:** `GET`
- **Summary:** Get a specific game by its ID.
- **Parameters:**
  - `game-id` (required, min: 1, max: Long.MAX_VALUE): ID of the game to retrieve.

# Genre Controller

**CRUD operations for Genre entities.**

This controller provides an endpoint for retrieving all available genres.

## Endpoints

### Get all genres

- **Endpoint:** `/api/v1/genres`
- **Method:** `GET`
- **Summary:** Get all available genres.

---

*Note: Make sure to replace placeholder names like `{game-id}` with actual values when making requests to the API.*

### Built With:

* Maven - Build tool
* Spring Boot - Web framework
* JUnit - Testing framework

## Authors:

* [SEM24](https://github.com/SEM24)
