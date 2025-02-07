package org.example.userservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDto {
    private String token;
    private String email;
}
