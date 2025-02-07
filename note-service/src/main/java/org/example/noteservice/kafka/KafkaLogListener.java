package org.example.noteservice.kafka;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaLogListener {
    private final KafkaClient kafkaClient;

    public KafkaLogListener(KafkaClient kafkaClient) {
        this.kafkaClient = kafkaClient;
    }

    @EventListener
    public void handleKafkaLogEvent(KafkaLogEvent event) {
        kafkaClient.publishMessage(event.getMessage());
    }
}