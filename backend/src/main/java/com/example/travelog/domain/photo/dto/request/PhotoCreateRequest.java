package com.example.travelog.domain.photo.dto.request;

import jakarta.validation.constraints.NotNull;

public record PhotoCreateRequest (
        @NotNull(message = "코멘트는 필수입니다.")
        String comment,
        String location
) {}
