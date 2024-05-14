package com.khomsi.backend.main.ai.service;

import com.khomsi.backend.main.ai.model.dto.ChatResponse;

import java.io.IOException;

public interface ChatService {
    ChatResponse sendRequestToAiChat(String text) throws IOException;
}
