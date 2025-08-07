package com.example.travelog.domain.travel.dto.response;

import java.time.LocalDate;

public record TravelListReadResponse(
        Long id,
        String title,
        String location,
        String thumbnailImage,
        LocalDate startDate,
        LocalDate endDate
) { }
