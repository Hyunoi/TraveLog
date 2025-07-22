package com.example.travelog.domain.user.controller;

import com.example.travelog.domain.user.dto.request.UserLogInRequest;
import com.example.travelog.domain.user.dto.request.UserProfileRequest;
import com.example.travelog.domain.user.dto.request.UserSignUpRequest;
import com.example.travelog.domain.user.dto.response.UserLoginResponse;
import com.example.travelog.domain.user.dto.response.UserMyPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "USER")
public interface UserController {
    @Operation(summary = "회원가입")
    public ResponseEntity<?> signUp(@Parameter(description = "이메일, 비밀번호, 닉네임 기입")
                                    @Valid @RequestBody UserSignUpRequest request);

    @Operation(summary = "로그인")
    public ResponseEntity<UserLoginResponse> logIn(@Parameter(description = "이메일, 비밀번호 기입")
                                                   @Valid @RequestBody UserLogInRequest request);

    @Operation(summary = "로그아웃")
    public ResponseEntity<?> logOut(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "[마이페이지] 회원 정보 조회")
    public ResponseEntity<UserMyPageResponse> getMyPage(@AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "[마이페이지] 회원 정보 수정")
    public ResponseEntity<?> updateProfile(@Parameter(description = "닉네임 기입")
                                           @RequestBody UserProfileRequest request,
                                           @AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "[마이페이지] 프로필 사진 등록/수정")
    public String updateProfileImage(@RequestPart("image") MultipartFile image,
                                     @AuthenticationPrincipal UserDetails userDetails);
}
