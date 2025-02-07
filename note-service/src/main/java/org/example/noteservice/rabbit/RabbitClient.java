package org.example.noteservice.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.example.noteservice.model.dto.EmailInfoDto;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitClient {
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey;

    public RabbitClient(RabbitTemplate rabbitTemplate,
                        @Value("${rabbit.exchange}") String exchangeName,
                        @Value("${rabbit.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
    }

    public void sendMessage(EmailInfoDto emailInfoDto) {
        try {
            log.info("Sending email notification: {}", emailInfoDto);
            rabbitTemplate.convertAndSend(exchangeName, routingKey, emailInfoDto);
            log.info("Email notification sent to RabbitMQ.");
        } catch (AmqpException e) {
            log.warn("Error sending notification", e);
        }
    }
}