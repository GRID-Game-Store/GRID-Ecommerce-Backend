package com.khomsi.backend.main.ai.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

public class GeminiRecords {

    public record GeminiRequest(List<Content> contents) {
    }

    public record Content(List<Part> parts) {
    }

    // "sealed" classes and interfaces:
    // - only "permitted" classes can implement the interface
    //   or extend the class
    public sealed interface Part
            permits TextPart {
    }

    public record TextPart(String text) implements Part {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record GeminiResponse(List<Candidate> candidates,
                                 PromptFeedback promptFeedback) {
        public record Candidate(Content content,
                                String finishReason,
                                int index,
                                List<SafetyRating> safetyRatings) {
            public record Content(List<TextPart> parts, String role) {
            }
        }
    }

    public record SafetyRating(String category, String probability) {
    }

    public record PromptFeedback(List<SafetyRating> safetyRatings) {
    }

    // Returned from "count" endpoint
    public record GeminiCountResponse(int totalTokens) {
    }

    // Models
    public record ModelList(List<Model> models) {
    }

    public record Model(
            String name,
            String version,
            String displayName,
            String description,
            int inputTokenLimit,
            int outputTokenLimit,
            List<String> supportedGenerationMethods,
            double temperature,
            double topP,
            int topK
    ) {
    }
}