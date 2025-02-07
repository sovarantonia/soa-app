package org.example.noteservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaClient {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaClient(KafkaTemplate<String, String> kafkaTemplate,
                       @Value("${spring.kafka.topic.logs}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void publishMessage(String message) {
        kafkaTemplate.send(topic, message);
        log.info("Sent to Kafka: " + message);
    }
}