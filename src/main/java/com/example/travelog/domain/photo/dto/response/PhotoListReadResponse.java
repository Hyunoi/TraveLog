package com.example.travelog.domain.photo.dto.response;

import java.time.LocalDateTime;

public record PhotoListReadResponse(
    Long photoId,
    String photoUrl,
    String comment,
    String location,
    LocalDateTime createdAt
) {}
