package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.main.checkout.model.entity.Transaction;
import com.khomsi.backend.main.checkout.model.entity.TransactionGames;
import com.khomsi.backend.main.game.model.dto.ShortGameModel;
import com.khomsi.backend.main.game.model.entity.Game;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
import com.khomsi.backend.main.user.model.dto.FullUserInfoDTO;
import com.khomsi.backend.main.user.model.entity.UserInfo;
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

import static com.khomsi.backend.main.checkout.model.enums.EmailTemplates.DISCOUNT_NOTIFICATION;
import static com.khomsi.backend.main.checkout.model.enums.EmailTemplates.PURCHASE_CONFIRMATION;

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
            FullUserInfoDTO userInfoDTO = userInfoService.getCurrentUser();
            prepareAndSendEmail(message, userInfoDTO.email(), PURCHASE_CONFIRMATION.getTemplateName(), prepareEmailContext(transaction));
        } catch (MessagingException e) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Async
    @Override
    public void sendDiscountNotificationEmail(List<ShortGameModel> discountedGames, UserInfo user) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            prepareAndSendEmail(message, user.getEmail(), DISCOUNT_NOTIFICATION.getTemplateName(),
                    prepareDiscountEmailContext(discountedGames, user));
        } catch (MessagingException e) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Context prepareEmailContext(Transaction transaction) {
        Context context = new Context();
        FullUserInfoDTO userInfoDTO = userInfoService.getCurrentUser();
        context.setVariable("userName", userInfoDTO.givenName());
        context.setVariable("orderId", transaction.getTransactionId());
        String formattedOrderDate = formatDate(transaction.getUpdatedAt());
        context.setVariable("orderDate", formattedOrderDate);
        addTransactionGamesToContext(transaction, context);
        context.setVariable("totalPrice", transaction.getTotalAmount());
        return context;
    }

    private Context prepareDiscountEmailContext(List<ShortGameModel> discountedGames, UserInfo user) {
        Context context = new Context();
        context.setVariable("userName", user.getEmail());
        context.setVariable("discountedGames", discountedGames);
        return context;
    }

    private void addTransactionGamesToContext(Transaction transaction, Context context) {
        if (transaction.getTransactionGames() != null && !transaction.getTransactionGames().isEmpty()) {
            List<Game> games = transaction.getTransactionGames().stream()
                    .map(TransactionGames::getGame)
                    .toList();
            context.setVariable("games", games);
        }
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }

    private String generateEmailContent(String template, Context context) {
        return templateEngine.process(template, context);
    }

    private void prepareAndSendEmail(MimeMessage message, String userEmail, String template, Context context) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(userEmail);
        helper.setFrom(mailSender);
        helper.setSubject(getEmailSubject(template));
        String htmlContent = generateEmailContent(template, context);
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }

    private String getEmailSubject(String template) {
        if (template.equalsIgnoreCase(PURCHASE_CONFIRMATION.getTemplateName())) {
            return PURCHASE_CONFIRMATION.getSubject();
        } else if (template.equalsIgnoreCase(DISCOUNT_NOTIFICATION.getTemplateName())) {
            return DISCOUNT_NOTIFICATION.getSubject();
        }
        return "";
    }
}