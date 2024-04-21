package com.khomsi.backend.main.utils.email.service;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.List;

public interface EmailService {
    @Async
    void sendPurchaseConfirmationEmail(Transaction transaction);

    @Async
    void sendBalanceUpdateNotification(String email, BigDecimal oldBalance, BigDecimal newBalance);

    @Async
    void sendDiscountNotificationEmail(List<ShortGameModel> discountedGames, UserInfo user);

    @Async
    void sendWarningEmail(String notification, UserInfo user);
}
