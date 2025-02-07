package org.example.notification.listener;

import lombok.extern.slf4j.Slf4j;
import org.example.notification.dto.EmailInfoDto;
import org.example.notification.service.EmailSendService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailListener {
    private final EmailSendService emailSendService;

    public EmailListener(final EmailSendService emailSendService) {
        this.emailSendService = emailSendService;
    }

    @RabbitListener(queues = "${rabbit.queue-name}")
    public void receiveMessage(EmailInfoDto message) {
        log.info("Received Email Message: {}", message);

        // Create email body content
        String emailBody = String.format(
                "Title: %s\nText: %s\nDate: %s\nGrade: %d",
                message.getTitle(), message.getText(), message.getDate(), message.getGrade()
        );

        // Send email
        emailSendService.sendNotificationEmail(message.getEmail(), emailBody);

        log.info("Email sent successfully to {}", message.getEmail());
    }
}
