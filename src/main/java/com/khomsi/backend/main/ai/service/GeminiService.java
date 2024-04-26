package com.khomsi.backend.main.ai.service;

import com.khomsi.backend.main.ai.GeminiInterface;
import com.khomsi.backend.main.ai.model.dto.GeminiRecords;
import com.khomsi.backend.main.ai.model.dto.GeminiRecords.Content;
import com.khomsi.backend.main.ai.model.dto.GeminiRecords.GeminiRequest;
import com.khomsi.backend.main.ai.model.dto.GeminiRecords.GeminiResponse;
import com.khomsi.backend.main.ai.model.dto.GeminiRecords.TextPart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {
    @Value("${app.googleai.api.model}")
    public String geminiPro;
    private final GeminiInterface geminiInterface;

    public GeminiRecords.GeminiResponse getCompletion(GeminiRequest request) {
        return geminiInterface.getCompletion(geminiPro, request);
    }

    public String getCompletion(String text) {
        GeminiResponse response = getCompletion(new GeminiRequest(
                List.of(new Content(List.of(new TextPart(text))))));
        return response.candidates().get(0).content().parts().get(0).text();
    }
 /*   public GeminiCountResponse countTokens(String model, GeminiRequest request) {
        return geminiInterface.countTokens(model, request);
    }

    public int countTokens(String text) {
        GeminiCountResponse response = countTokens(GEMINI_PRO, new GeminiRequest(
                List.of(new Content(List.of(new TextPart(text))))));
        return response.totalTokens();
    }*/
}
