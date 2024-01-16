package com.khomsi.backend.game;

import com.khomsi.grid.config.SpringIntegration;
import com.khomsi.grid.main.game.model.entity.Game;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
public class GameServiceTest extends SpringIntegration {
    private MockMvc mockMvc;
    //    private Long requestedGameId;
    private Game retrievedGame;
    @Autowired
    private WebApplicationContext context;

    @Given("the database contains a game with ID")
    public void givenTheDatabaseContainsAGameWithID() {
//        gameRepository.save(Game.builder()
//                .id(gameId)
//                .title("Sample Game")
//                .build());
    }

//    @Given("the database does not contain a game with ID {long}")
//    public void givenTheDatabaseDoesNotContainAGameWithID(Long gameId) {
//        //        gameRepository.findById(gameId).ifPresent(gameRepository::delete);
//    }

    @When("the user requests the game with ID {long}")
    public void whenTheUserRequestsTheGameWithID(long gameId) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        Game game = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(mvcResult.getResponse()
                        .getContentAsString(), Game.class);
        retrievedGame.setId(game.getId());
    }
/*    @When("the user requests the game with ID {long}")
    public void whenTheUserRequestsTheGameWithID(long gameId) throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/games/" + gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
        retrievedGame = new ObjectMapper()
                .readValue(mvcResult.getResponse()
                        .getContentAsString(), Game.class);
    }*/

//    @When("the user retrieve game that doesn't exist in db with ID {long}")
//    public void theUserRetrieveGameThatDoesnTExistInDbWithID(Long gameId) {
////        requestedGameId = gameId;
//    }

    @Then("the system returns the game details")
    public void thenTheSystemReturnsTheGameDetails() {
        assertEquals("Mafia: Definitive Edition", retrievedGame.getTitle());
    }

//    @Then("the system should indicate that the game is not found")
//    public void thenTheSystemShouldIndicateThatTheGameIsNotFound() {
//        assertThrows(GameNotFoundException.class, () -> gameService.getGameById(retrievedGame.getId()));
//    }
}
