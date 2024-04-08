package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.user.model.entity.UserInfo;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface EmailService {
    void sendPurchaseConfirmationEmail(Transaction transaction);
    @Async
    void sendDiscountNotificationEmail(List<ShortGameModel> discountedGames, UserInfo user);
}
