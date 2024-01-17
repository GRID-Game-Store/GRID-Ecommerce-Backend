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