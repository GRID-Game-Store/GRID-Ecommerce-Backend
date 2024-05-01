package com.khomsi.backend.main.checkout.repository;

import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface TransactionGamesRepository extends JpaRepository<TransactionGames, Long> {
    @Query("SELECT COUNT(tg) FROM TransactionGames tg WHERE tg.games.id = :gameId")
    Long countTransactionsByGameId(@Param("gameId") Long gameId);
    @Query("SELECT COALESCE(SUM(tg.priceOnPay), 0) FROM TransactionGames tg WHERE tg.games.id = :gameId")
    BigDecimal sumTotalRevenueByGameId(@Param("gameId") Long gameId);
}
