package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendPurchaseConfirmationEmail(Transaction transaction);
}
