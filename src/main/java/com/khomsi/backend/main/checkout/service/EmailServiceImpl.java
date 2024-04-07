package com.khomsi.backend.main.checkout.service;

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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String mailSender;
    private final UserInfoService userInfoService;

    @Async
    @Override
    public void sendPurchaseConfirmationEmail(Transaction transaction) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            FullUserInfoDTO userInfoDTO = userInfoService.getCurrentUser();
            helper.setTo(userInfoDTO.email());
            helper.setFrom(mailSender);
            helper.setSubject("Thanks for purchase in GRID");

            Context context = prepareEmailContext(transaction, userInfoDTO);
            String htmlContent = generateEmailContent("purchase_confirmation_email", context);
            sendEmail(message, htmlContent);
        } catch (MessagingException e) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Context prepareEmailContext(Transaction transaction, FullUserInfoDTO userInfoDTO) {
        Context context = new Context();
        context.setVariable("userName", userInfoDTO.givenName());
        context.setVariable("orderId", transaction.getTransactionId());
        String formattedOrderDate = formatDate(transaction.getUpdatedAt());
        context.setVariable("orderDate", formattedOrderDate);
        addTransactionGamesToContext(transaction, context);
        context.setVariable("totalPrice", transaction.getTotalAmount());
        return context;
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }

    private void addTransactionGamesToContext(Transaction transaction, Context context) {
        if (transaction.getTransactionGames() != null && !transaction.getTransactionGames().isEmpty()) {
            List<Game> games = transaction.getTransactionGames().stream()
                    .map(TransactionGames::getGame)
                    .toList();
            context.setVariable("games", games);
        }
    }

    private String generateEmailContent(String template, Context context) {
        return templateEngine.process(template, context);
    }

    private void sendEmail(MimeMessage message, String htmlContent) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}