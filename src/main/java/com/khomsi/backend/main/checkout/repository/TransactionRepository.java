package com.khomsi.backend.main.checkout.repository;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
