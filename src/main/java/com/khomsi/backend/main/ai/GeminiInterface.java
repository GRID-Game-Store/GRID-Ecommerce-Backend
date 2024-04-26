package com.khomsi.backend.main.ai;

import com.khomsi.backend.main.ai.model.dto.GeminiRecords.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1/models/")
public interface GeminiInterface {
/*    @GetExchange
    ModelList getModels();

    @PostExchange("{model}:countTokens")
    GeminiRecords.GeminiCountResponse countTokens(
            @PathVariable String model,
            @RequestBody GeminiRecords.GeminiRequest request);
            */

    @PostExchange("{model}:generateContent")
    GeminiResponse getCompletion(
            @PathVariable String model,
            @RequestBody GeminiRequest request);
}