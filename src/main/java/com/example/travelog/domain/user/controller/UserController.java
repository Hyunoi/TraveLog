package com.example.travelog.domain.user.controller;

import com.example.travelog.domain.user.dto.request.UserLogInRequest;
import com.example.travelog.domain.user.dto.request.UserProfileRequest;
import com.example.travelog.domain.user.dto.request.UserSignUpRequest;
import com.example.travelog.domain.user.dto.response.UserLoginResponse;
import com.example.travelog.domain.user.dto.response.UserMyPageResponse;
import com.example.travelog.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    @GetMapping("/mypage")
    public ResponseEntity<UserMyPageResponse> getMyPage(@RequestHeader("Authorization") String accessToken) {
        UserMyPageResponse response = userService.getMyPage(accessToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileRequest request,
                              @RequestHeader("Authorization") String accessToken) {
        userService.updateProfile(request, accessToken);
        return ResponseEntity.ok().build();
    }
}
