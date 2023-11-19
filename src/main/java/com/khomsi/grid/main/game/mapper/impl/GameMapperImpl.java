package com.khomsi.grid.main.game.mapper.impl;

import com.khomsi.grid.main.game.mapper.GameMapper;
import com.khomsi.grid.main.game.model.dto.GameModelWithLimit;
import com.khomsi.grid.main.game.model.dto.PopularGameModel;
import com.khomsi.grid.main.game.model.dto.ShortGameModel;
import com.khomsi.grid.main.game.model.entity.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GameMapperImpl implements GameMapper {
    @Override
    public ShortGameModel toShortGame(Game game) {
        return ShortGameModel.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .coverImageUrl(game.getCoverImageUrl())
                .price(game.getPrice())
                .build();
    }

    @Override
    public PopularGameModel toPopularGame(Game game) {
        return PopularGameModel.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .coverImageUrl(game.getGameMedia().getBannerUrl())
                .price(game.getPrice())
                .genres(game.getGenres())
                .build();
    }

    @Override
    public GameModelWithLimit toLimitGame(Game game) {
        return GameModelWithLimit.builder()
                .id(game.getId())
                .title(game.getTitle())
                .description(game.getDescription())
                .coverImageUrl(game.getCoverImageUrl())
                .price(game.getPrice())
                .genres(game.getGenres().stream().limit(2).collect(Collectors.toSet()))
                .build();
    }
}