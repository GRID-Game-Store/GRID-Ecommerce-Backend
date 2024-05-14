package com.khomsi.backend.main.game.mapper.impl;

import com.khomsi.backend.main.game.mapper.GameMapper;
import com.khomsi.backend.main.ai.model.dto.AiChatGameModel;
import com.khomsi.backend.main.game.model.dto.GameModelWithGenreLimit;
import com.khomsi.backend.main.game.model.dto.PopularGameModel;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GameMapperImpl implements GameMapper {
    @Override
    public ShortGameModel toShortGame(Game game, boolean ownedByCurrentUser) {
        return ShortGameModel.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .coverImageUrl(game.getCoverImageUrl())
                .price(game.getPrice())
                .discount(game.getDiscount())
                .active(game.getActive())
                .ownedByCurrentUser(ownedByCurrentUser)
                .build();
    }

    @Override
    public ShortGameModel toShortGame(Game game) {
        return toShortGame(game, false);
    }

    @Override
    public AiChatGameModel toAiChatGameModel(Game game) {
        return AiChatGameModel.builder()
                .id(game.getId())
                .title(game.getTitle())
                .price(game.getPrice())
                .build();
    }

    @Override
    public PopularGameModel toPopularGame(Game game, boolean ownedByCurrentUser) {
        return PopularGameModel.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .coverImageUrl(game.getGameMedia().getBannerUrl())
                .price(game.getPrice())
                .discount(game.getDiscount())
                .genres(game.getGenres())
                .ownedByCurrentUser(ownedByCurrentUser)
                .build();
    }

    @Override
    public GameModelWithGenreLimit toLimitGenreGame(Game game, boolean ownedByCurrentUser) {
        return GameModelWithGenreLimit.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .coverImageUrl(game.getCoverImageUrl())
                .price(game.getPrice())
                .discount(game.getDiscount())
                .genres(game.getGenres().stream().limit(2).collect(Collectors.toSet()))
                .ownedByCurrentUser(ownedByCurrentUser)
                .build();
    }
}