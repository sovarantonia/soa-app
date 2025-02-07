package org.example.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.JwtResponseDto;
import org.example.userservice.model.dto.UserLoginDto;
import org.example.userservice.model.dto.UserRequestDto;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmail(userLoginDto.getEmail()).orElseThrow(EntityNotFoundException::new);
        String token = tokenService.generateToken(userLoginDto.getEmail());
        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            return JwtResponseDto
                    .builder()
                    .token(token)
                    .email(user.getEmail())
                    .build();
        }

        return JwtResponseDto
                .builder()
                .token("No valid token")
                .email(userLoginDto.getEmail())
                .build();
    }

    public User register(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email address already in use");
        }
        User user = User
                .builder()
                .email(userRequestDto.getEmail())
                .name(userRequestDto.getName())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .build();

        return userRepository.save(user);
    }
}
