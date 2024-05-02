package com.khomsi.backend.main.ai.controller;

import com.khomsi.backend.main.ai.model.dto.ChatResponse;
import com.khomsi.backend.main.ai.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{text}")
    public ChatResponse chat(@PathVariable String text) throws IOException {
        return chatService.sendRequestToAiChat(text);
    }
}