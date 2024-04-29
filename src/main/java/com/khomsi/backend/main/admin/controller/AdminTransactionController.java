package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.model.request.EntityModelRequest;
import com.khomsi.backend.main.admin.model.response.AdminModelResponse;
import com.khomsi.backend.main.admin.service.AdminTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Admin-Transaction", description = "CRUD operation for Admin-Transaction Controller")
@RequestMapping("/api/v1/admin/transactions")
@Validated
@RequiredArgsConstructor
public class AdminTransactionController {
    private final AdminTransactionService adminTransactionService;
    @GetMapping
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Get all transactions")
    @ResponseStatus(HttpStatus.OK)
    public AdminModelResponse showAllTransactionsByPage(@Valid EntityModelRequest entityModelRequest) {
        return adminTransactionService.getAllTransactions(entityModelRequest);
    }
}
