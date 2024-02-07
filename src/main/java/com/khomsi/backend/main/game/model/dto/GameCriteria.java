package com.khomsi.backend.main.game.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameCriteria {
    @PositiveOrZero
    int page = 0;
    @Min(1)
    @Max(Integer.MAX_VALUE)
    int size = 5;
    String title;
    BigDecimal maxPrice;
    String genres;
    String platforms;
    String tags;
    String developers;
    String publishers;
    String[] sort = {"id,desc"};
}
