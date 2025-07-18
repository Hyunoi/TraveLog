package com.example.travelog.domain.travel.controller;

import com.example.travelog.domain.travel.dto.request.TravelCreateRequest;
import com.example.travelog.domain.travel.dto.request.TravelUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "TRAVEL")
public interface TravelController {
    @Operation(summary = "여행 생성")
    public void createTravel(@Parameter(description = "여행명, 설명, 시작/종료 일자, 여행 장소 기입")
                             @Valid @RequestBody TravelCreateRequest request,
                             @AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "여행 섬네일 지정")
    public String updateThumbnail(@PathVariable Long travelId,
                                  @RequestPart("image") MultipartFile image,
                                  @AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "여행 수정")
    public ResponseEntity<?> updateTravel(@Parameter(description = "여행명, 설명, 시작/종료 일자 기입")
                                          @PathVariable Long travelId,
                                          @RequestBody TravelUpdateRequest request,
                                          @AuthenticationPrincipal UserDetails userDetails);
}
