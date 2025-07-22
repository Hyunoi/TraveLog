package com.example.travelog.domain.travel.dto.response;

public record TravelListReadResponse(
        String title,
        String location,
        String thumbnailImage
) { }
