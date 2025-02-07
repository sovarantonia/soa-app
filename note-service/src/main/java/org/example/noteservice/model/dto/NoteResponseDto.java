package org.example.noteservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NoteResponseDto {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private LocalDate date;
    private Integer grade;
}
