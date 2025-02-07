package org.example.noteservice.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoteRequestDto {
    private Long userId;
    private String title = "";
    private String text = "";
    private LocalDate date;
    private Integer grade = 0;
}
