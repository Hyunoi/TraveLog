package com.example.travelog.domain.travel.dto.response;

public record TravelResponse(
        String title,
        String description,
        String location,
        String thumbnailImage
) { }
