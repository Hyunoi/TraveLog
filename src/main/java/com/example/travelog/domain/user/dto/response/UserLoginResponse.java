package com.example.travelog.domain.user.dto.response;

public record UserLoginResponse(
        String accessToken,
        String refreshToken
) { }
