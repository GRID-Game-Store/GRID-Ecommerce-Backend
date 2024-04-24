package com.khomsi.backend.main.admin.service;

import java.math.BigDecimal;
import java.util.Map;

public interface AdminMetricService {
    Map<String, Long> getTotalTransactionsByGameId(Long gameId);

    Map<String, Long> getTotalUsersOnWebsite();

    Map<String, BigDecimal> getTotalRevenueByGameId(Long gameId);

    Map<String, Object> getRevenueSummaryForYear(int year);
}
