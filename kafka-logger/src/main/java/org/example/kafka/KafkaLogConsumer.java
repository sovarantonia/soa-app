package org.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaLogConsumer {
    @KafkaListener(topics = "${kafka-topic}", groupId = "log-group")
    public void consumeMessage(String message) {
        log.info(message);
    }
}