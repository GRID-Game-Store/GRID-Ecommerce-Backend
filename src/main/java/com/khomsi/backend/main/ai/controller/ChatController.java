package com.khomsi.backend.main.ai.controller;

import com.khomsi.backend.main.ai.model.dto.ChatResponse;
import com.khomsi.backend.main.ai.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Validated
@Tag(name = "Chat", description = "Chat API")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{text}")
    @Operation(summary = "Input in chat")
    public ChatResponse chat(@PathVariable @Valid @NotNull @NotEmpty String text) throws IOException {
        return chatService.sendRequestToAiChat(text);
    }
}