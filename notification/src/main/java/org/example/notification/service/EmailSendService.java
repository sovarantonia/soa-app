package org.example.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSendService {
    private final JavaMailSender javaMailSender;

    @Value("${sender-email-address}")
    private String senderEmail;

    public EmailSendService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationEmail(String email, String description) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom(senderEmail);
            message.setSubject("New Note from Application");
            message.setText(description);

            javaMailSender.send(message);
            log.info("Email sent to {}", email);
        } catch (Exception e) {
            log.error("Failed to send email to {}", email, e);
        }
    }
}
