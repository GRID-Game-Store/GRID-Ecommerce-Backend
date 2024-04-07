package com.khomsi.backend.main.email;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.service.UserInfoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String mailSender;
    private final UserInfoService userInfoService;

    @Async
    public void sendPurchaseConfirmationEmail(Transaction transaction, String email) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            FullUserInfoDTO userInfoDTO = userInfoService.getCurrentUser();
            helper.setTo(email);
            helper.setFrom(mailSender);
            helper.setSubject("Thanks for purchase in GRID");

            Context context = new Context();
            context.setVariable("userName", userInfoDTO.givenName());
            context.setVariable("orderId", transaction.getTransactionId());

            // Reformat data for email
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String formattedOrderDate = transaction.getUpdatedAt().format(formatter);
            context.setVariable("orderDate", formattedOrderDate);
            // Get the list of games from transaction and pass it as a context variable
            List<Game> games = transaction.getTransactionGames().stream().map(TransactionGames::getGame).toList();
            context.setVariable("games", games);

            context.setVariable("totalPrice", transaction.getTotalAmount());

            String htmlContent = templateEngine.process("purchase_confirmation_email", context);
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}