package com.khomsi.backend.main.checkout.controller;

import com.khomsi.backend.main.checkout.model.dto.Mail;
import com.khomsi.backend.main.checkout.service.EmailService;
import com.khomsi.backend.main.game.model.entity.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Email", description = "CRUD operation for Email Controller")
@RequestMapping("/api/v1/email")
@Validated
@RequiredArgsConstructor
public class EmailTestController {
    private final EmailService emailService;

    @PostMapping("/send")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Test email")
    public String sendEmail(String email) {
        Game game = new Game();
        game.setTitle("Mafia: Definitive Edition");
        game.setPrice(BigDecimal.TEN);
        game.setCoverImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1030840/header.jpg?t=1632420251");
        emailService.sendPurchaseConfirmationEmail(Mail.builder()
                .recipientEmail(email)
                .games(List.of(game))
                .paymentMethod("Test payment")
                .build());
        return "Send test email success.";
    }
}
