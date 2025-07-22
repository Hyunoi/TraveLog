package com.example.travelog.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileRequest (
        @NotBlank(message = "닉네임을 필수 입력값입니다.")
        @Size(min = 2, max = 14)
        String nickname
){ }
