package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.main.admin.model.dto.GameInsertDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.game.GameRepository;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl {
    private final JdbcTemplate jdbcTemplate;
    private final GameRepository gameRepository;

    @Transactional
    public AdminResponse addGameToDb(GameInsertDTO gameDTO) {
        // Call the stored procedure to insert the game
        jdbcTemplate.update("CALL insert_game(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                gameDTO.gameTitle(), gameDTO.gameDescription(), gameDTO.gameReleaseDate(), gameDTO.gameSystemRequirements(),
                gameDTO.gamePrice(), gameDTO.gameDiscount(), gameDTO.gamePermittedAge(), gameDTO.gameCoverImageUrl(),
                gameDTO.gameBannerImageUrl(), gameDTO.gameTrailerUrl(), gameDTO.gameScreenshotUrl(), gameDTO.gameTrailerScreenshotUrl(),
                gameDTO.developerName(), gameDTO.publisherName(), gameDTO.tags(), gameDTO.genres(), gameDTO.platforms());
        Game game = gameRepository.findByTitleIgnoreCase(gameDTO.gameTitle()).orElseThrow(()
                -> new GlobalServiceException(HttpStatus.NOT_FOUND));
        return AdminResponse.builder()
                .response("Game with id " + game.getId() + " was added!").build();
    }
}
