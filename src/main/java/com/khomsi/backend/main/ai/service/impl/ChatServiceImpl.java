package com.khomsi.backend.main.ai.service.impl;

import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import com.khomsi.backend.main.ai.model.dto.AiChatGameModel;
import com.khomsi.backend.main.ai.model.dto.ChatResponse;
import com.khomsi.backend.main.ai.service.ChatService;
import com.khomsi.backend.main.game.GameRepository;
import com.khomsi.backend.main.game.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    @Value("${app.googleai.api.prompt-template}")
    private String templateText;
    private final ChatSession chatSession;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public ChatResponse sendRequestToAiChat(String text) throws IOException {
        String requestText = getRequestText(text);
        GenerateContentResponse generateContentResponse = this.chatSession.sendMessage(requestText);
        return new ChatResponse(ResponseHandler.getText(generateContentResponse));
    }

    private String getRequestText(String text) {
        List<AiChatGameModel> games = gameRepository.findAllActiveGames()
                .stream().map(gameMapper::toAiChatGameModel).toList();
        return templateText.replace("{games}", games.toString())
                .replace("{text}", text);
    }
}
