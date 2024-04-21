package com.khomsi.backend.main.admin.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntityModelRequest {
    //TODO add gameId also
    @PositiveOrZero
    int page = 0;
    @Min(1)
    @Max(Integer.MAX_VALUE)
    int size = 5;
    String[] sort;
}