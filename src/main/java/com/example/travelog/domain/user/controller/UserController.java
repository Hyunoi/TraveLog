package com.example.travelog.domain.user.controller;

import com.example.travelog.domain.user.dto.request.UserLogInRequest;
import com.example.travelog.domain.user.dto.request.UserSignUpRequest;
import com.example.travelog.domain.user.dto.response.UserLoginResponse;
import com.example.travelog.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> logIn(@Valid @RequestBody UserLogInRequest request) {
        UserLoginResponse response = userService.logIn(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(@RequestHeader("Authorization") String accessToken) {
        userService.logOut(accessToken);
        return ResponseEntity.ok().build();
    }
}
