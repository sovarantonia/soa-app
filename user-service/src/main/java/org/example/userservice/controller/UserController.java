package org.example.userservice.controller;

import lombok.AllArgsConstructor;
import org.example.userservice.model.User;
import org.example.userservice.model.dto.*;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(userService.login(userLoginDto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.register(userRequestDto);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
