package com.khomsi.backend.main.checkout.repository;

import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionGamesRepository extends JpaRepository<TransactionGames, Long> {
}
