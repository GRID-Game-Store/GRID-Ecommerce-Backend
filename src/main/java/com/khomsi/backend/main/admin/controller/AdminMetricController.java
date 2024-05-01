package com.khomsi.backend.main.admin.controller;

import com.khomsi.backend.main.admin.service.AdminMetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;

@RestController
@Tag(name = "Admin-Metric", description = "CRUD operation for Admin-Metric Controller")
@RequestMapping("/api/v1/admin/metrics")
@Validated
@RequiredArgsConstructor
public class AdminMetricController {
    private final AdminMetricService adminMetricService;

    @GetMapping("/total/qty/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Count times of game purchase")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> showPurchaseQty(@PathVariable("game-id") Long gameId) {
        return adminMetricService.getTotalTransactionsByGameId(gameId);
    }

    @GetMapping("/total")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Revenue Summary For Year")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> showRevenueSummaryForYear(@RequestParam("year") @Valid @Min(2000) int year) {
        return adminMetricService.getRevenueSummaryForYear(year);
    }

    @GetMapping("/total/users")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Total qty of users on Website")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Long> showUsersQtyOnWebsite() {
        return adminMetricService.getTotalUsersOnWebsite();
    }

    @GetMapping("/total/amount/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Total amount of game purchase")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, BigDecimal> showPurchaseAmount(@PathVariable("game-id") Long gameId) {
        return adminMetricService.getTotalRevenueByGameId(gameId);
    }
}
