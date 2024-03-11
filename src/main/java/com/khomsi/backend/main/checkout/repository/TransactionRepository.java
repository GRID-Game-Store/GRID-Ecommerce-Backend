package com.khomsi.backend.main.checkout.repository;

import com.khomsi.backend.main.checkout.model.dto.TransactionDTO;
import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findAllByUsersOrderByCreatedAtDesc(UserInfo userInfo);
//    List<Transaction> findAllByUsers(UserInfo userInfo);
}
