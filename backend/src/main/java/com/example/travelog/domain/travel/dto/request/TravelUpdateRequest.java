package com.example.travelog.domain.travel.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TravelUpdateRequest(
        @Size(min = 1, max = 20, message = "여행명은 1~20자 지정 가능합니다.")
        String title,

        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
