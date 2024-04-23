package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.main.checkout.repository.TransactionGamesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminMetricServiceImpl {
    private final TransactionGamesRepository transactionGamesRepository;

    public Map<String, Long> getTotalTransactionsByGameId(Long gameId) {
        Long countGames = transactionGamesRepository.countTransactionsByGameId(gameId);
        Map<String, Long> result = new HashMap<>();
        result.put("totalTransactions", countGames);
        return result;
    }

    public Map<String, BigDecimal> getTotalRevenueByGameId(Long gameId) {
        Map<String, BigDecimal> result = new HashMap<>();
        BigDecimal sumTotalRevenue = transactionGamesRepository.sumTotalRevenueByGameId(gameId);
        result.put("sumTotalRevenue", sumTotalRevenue);
        return result;
    }
}
