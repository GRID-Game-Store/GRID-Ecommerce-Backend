Feature: Retrieving a game by ID

  Scenario: Successful retrieval of a game by ID
    Given the database contains a game with ID
    When the user requests the game with ID 1
    Then the system returns the game details

#  Scenario: Attempting to retrieve a non-existent game
#    Given the database does not contain a game with ID 999
#    When the user retrieve game that doesn't exist in db with ID 999
#    Then the system should indicate that the game is not found