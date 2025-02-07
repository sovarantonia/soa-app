package org.example.notification.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailInfoDto implements Serializable {
    private String email;
    private String title;
    private String text;
    private String date;
    private Integer grade;
}
