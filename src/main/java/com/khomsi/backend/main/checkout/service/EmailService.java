package com.khomsi.backend.main.checkout.service;

import com.khomsi.backend.main.checkout.model.dto.Mail;
import com.khomsi.backend.main.handler.exception.GlobalServiceException;
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

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String mailSender;

    @Async
    public void sendPurchaseConfirmationEmail(Mail mail) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(mail.recipientEmail());
            helper.setFrom(mailSender);
            helper.setSubject("Thanks for purchase in GRID");

            Context context = new Context();
            context.setVariable("email", mail.recipientEmail());
            mail.games().forEach(game -> {
                context.setVariable("gameBanner", game.getCoverImageUrl());
                context.setVariable("gameName", game.getTitle());
                context.setVariable("gamePrice", game.getPrice());
            });
            context.setVariable("paymentMethod", mail.paymentMethod());

            String htmlContent = templateEngine.process("purchase_confirmation_email", context);
            helper.setText(htmlContent, true);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new GlobalServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}