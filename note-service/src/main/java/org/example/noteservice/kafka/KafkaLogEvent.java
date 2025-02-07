package org.example.noteservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KafkaLogEvent {
    private String message;
}
