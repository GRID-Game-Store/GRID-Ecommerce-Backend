package com.khomsi.backend.main.email;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.email.EmailService;
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
import java.time.LocalDateTime;
import java.util.*;

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
        game.setPrice(BigDecimal.valueOf(12314124));
        game.setDescription("tetstsefsfdasfdasdfasdfasfgafsdggsddasfaFDADSFADSFAFDSASFDADFAFSDASFDASFDASDF");
        game.setCoverImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1030840/header.jpg?t=1632420251");

        Game game2 = new Game();
        game2.setTitle("Mafia22: Definitive Edition");
        game2.setPrice(BigDecimal.valueOf(3311));
        game2.setDescription("tetstsefsfdasfdasdfasdfasfgafsdggsddasfaFDADSFADSFAFDSASFDADFAFSDASFDASFDASDF");
        game2.setCoverImageUrl("https://cdn.cloudflare.steamstatic.com/steam/apps/1030840/header.jpg?t=1632420251");

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setUpdatedAt(LocalDateTime.now());
        transaction.setTotalAmount(BigDecimal.valueOf(1111));

        // Create a new TransactionGames instance and associate it with the Transaction
        TransactionGames transactionGame = new TransactionGames();
        transactionGame.setGame(game);
        transactionGame.setPriceOnPay(game.getPrice());
        transactionGame.setTransactions(transaction);

        TransactionGames transactionGame2 = new TransactionGames();
        transactionGame2.setGame(game2);
        transactionGame2.setPriceOnPay(game2.getPrice());
        transactionGame2.setTransactions(transaction);

        // Create a HashSet to hold TransactionGames instances
        Set<TransactionGames> transactionGames = new HashSet<>();
        transactionGames.add(transactionGame);
        transactionGames.add(transactionGame2);

        // Set the transactionGames field of the Transaction object
        transaction.setTransactionGames(transactionGames);

        emailService.sendPurchaseConfirmationEmail(transaction, email);
        return "Send test email success.";
    }
}
