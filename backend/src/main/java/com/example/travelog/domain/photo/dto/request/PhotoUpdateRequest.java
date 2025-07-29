package com.example.travelog.domain.photo.dto.request;

public record PhotoUpdateRequest(
        String comment,
        String location
) {
}
