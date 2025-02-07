package org.example.noteservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailInfoDto implements Serializable {
    private String email;
    private String title;
    private String text;
    private String date;
    private Integer grade;
}