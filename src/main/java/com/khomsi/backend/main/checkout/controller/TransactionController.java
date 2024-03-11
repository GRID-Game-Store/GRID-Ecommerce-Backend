package com.khomsi.backend.main.checkout.controller;

import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.response.TransactionResponse;
import com.khomsi.backend.main.checkout.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Transaction", description = "CRUD operation for Transaction Controller")
@RequestMapping("/api/v1/transactions")
@Validated
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Get all transactions for user")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.transactionList();
    }

    @PostMapping("/revert")
    @ResponseStatus(HttpStatus.OK)
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Revert transaction for user in case of cancel")
    public TransactionResponse revertTransactionToCart(@RequestParam("sessionId") String sessionId) {
        return transactionService.returnTransactionToCart(sessionId);
    }
}
