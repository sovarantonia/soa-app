package org.example.userservice.model.dto;

import lombok.Data;

/**
 * for user registration or credential update
 */
@Data
public class UserRequestDto {
    private String name = "";
    private String email = "";
    private String password = "";
}
