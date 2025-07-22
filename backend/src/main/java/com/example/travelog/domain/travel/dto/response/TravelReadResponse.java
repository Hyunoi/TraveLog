package com.example.travelog.domain.travel.dto.response;

public record TravelReadResponse(
        String title,
        String description,
        String location,
        String thumbnailImage
) { }
