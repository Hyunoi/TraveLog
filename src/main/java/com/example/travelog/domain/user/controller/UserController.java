package com.example.travelog.domain.user.controller;

import com.example.travelog.domain.s3.service.S3ImageService;
import com.example.travelog.domain.user.dto.request.UserLogInRequest;
import com.example.travelog.domain.user.dto.request.UserProfileRequest;
import com.example.travelog.domain.user.dto.request.UserSignUpRequest;
import com.example.travelog.domain.user.dto.response.UserLoginResponse;
import com.example.travelog.domain.user.dto.response.UserMyPageResponse;
import com.example.travelog.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final S3ImageService s3ImageService;

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
    public ResponseEntity<?> logOut(@AuthenticationPrincipal UserDetails userDetails) {
        userService.logOut(userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mypage")
    public ResponseEntity<UserMyPageResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails) {
        UserMyPageResponse response = userService.getMyPage(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileRequest request,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateProfile(request, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/profile-image")
    public String updateProfileImage(@RequestPart("image") MultipartFile image,
                                   @AuthenticationPrincipal UserDetails userDetails) {
        String imageUrl = s3ImageService.upload(image);
        userService.updateProfileImage(imageUrl, userDetails.getUsername());
        return imageUrl;
    }
}
