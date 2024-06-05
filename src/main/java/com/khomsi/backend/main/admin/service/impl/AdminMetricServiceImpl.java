package com.khomsi.backend.main.admin.service.impl;

import com.khomsi.backend.main.admin.service.AdminMetricService;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.enums.PaymentMethod;
import com.khomsi.backend.main.checkout.repository.TransactionGamesRepository;
import com.khomsi.backend.main.checkout.repository.TransactionRepository;
import com.khomsi.backend.main.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMetricServiceImpl implements AdminMetricService {
    private final TransactionGamesRepository transactionGamesRepository;
    private final TransactionRepository transactionRepository;
    private final UserInfoRepository userInfoRepository;

    @Override
    public Map<String, Long> getTotalTransactionsByGameId(Long gameId) {
        return Map.of("totalTransactions", transactionGamesRepository.countTransactionsByGameId(gameId));
    }

    @Override
    public Map<String, Long> getTotalUsersOnWebsite() {
        return Map.of("totalUsers", userInfoRepository.count());
    }
    @Override
    public Map<String, Long> getTotalUsersWithGamesOnWebsite() {
        return Map.of("totalUsersWithGames", userInfoRepository.countUsersWithAtLeastOneGame());
    }
    @Override
    public Map<String, BigDecimal> getTotalRevenueByGameId(Long gameId) {
        return Map.of("sumTotalRevenue", transactionGamesRepository.sumTotalRevenueByGameId(gameId));
    }

    @Override
    public Map<String, Object> getRevenueSummaryForYear(int year) {
        List<Transaction> transactions = transactionRepository.findAllByYear(year);
        Map<String, Object> revenueSummary = new LinkedHashMap<>();
        // Group into month to reduce amount of data calling in db
        Map<String, List<Transaction>> transactionsByMonth = transactions.stream()
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getCreatedAt().getMonth().toString()
                ));
        transactionsByMonth.forEach((month, monthTransactions) -> {
            // Get the total amount and number of transactions for the current month
            Map<String, Object> monthSummary = getSummaryForMonth(monthTransactions);
            // Add the current month to the general map
            revenueSummary.put(month, monthSummary);
        });
        // Get the total amount and number of transactions for the whole year
        Map<String, Object> yearSummary = getSummaryForMonth(transactions);

        // Adding general information for the year to the general map
        revenueSummary.put("yearSummary", yearSummary);

        return revenueSummary;
    }

    private Map<String, Object> getSummaryForMonth(List<Transaction> transactions) {
        BigDecimal totalRevenue = transactions.stream()
                .map(Transaction::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long totalTransactions = transactions.size();
        long totalSaleGames = transactions.stream().map(Transaction::getTransactionGames)
                .count();
        long stripePayments = transactions.stream()
                .filter(transaction -> transaction.getPaymentMethods()
                        .equalsIgnoreCase(PaymentMethod.STRIPE.toString()))
                .count();
        long paypalPayments = transactions.stream()
                .filter(transaction -> transaction.getPaymentMethods()
                        .equalsIgnoreCase(PaymentMethod.PAYPAL.toString()))
                .count();

        Map<String, Object> monthSummary = new HashMap<>();
        monthSummary.put("totalRevenue", totalRevenue);
        monthSummary.put("totalTransactions", totalTransactions);
        monthSummary.put("totalSaleGames", totalSaleGames);
        monthSummary.put("stripePayments", stripePayments);
        monthSummary.put("payPalPayments", paypalPayments);
        return monthSummary;
    }
}
