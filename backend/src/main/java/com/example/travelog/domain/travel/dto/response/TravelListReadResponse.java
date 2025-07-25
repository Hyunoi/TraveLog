package com.example.travelog.domain.travel.dto.response;

public record TravelListReadResponse(
        Long id,
        String title,
        String location,
        String thumbnailImage
) { }
