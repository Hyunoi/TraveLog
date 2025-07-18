package com.example.travelog.domain.travel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TravelCreateRequest(
        @NotBlank(message = "여행명은 필수 입력값입니다.")
        @Size(min = 1, max = 20, message = "여행명은 1~20자 지정 가능합니다.")
        String title,

        String description,

        @NotBlank(message = "여행 위치는 필수 입력값입니다.")
        String location,

        @NotBlank(message = "시작일은 필수 입력 값입니다.")
        LocalDateTime startDate,

        @NotBlank(message = "마감일은 필수 입력 값입니다.")
        LocalDateTime endDate,

        double latitude,
        double longitude
) { }
