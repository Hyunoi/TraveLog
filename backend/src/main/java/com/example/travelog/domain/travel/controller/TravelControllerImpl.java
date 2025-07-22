package com.example.travelog.domain.travel.controller;

import com.example.travelog.global.s3.service.S3ImageService;
import com.example.travelog.domain.travel.dto.request.TravelCreateRequest;
import com.example.travelog.domain.travel.dto.request.TravelUpdateRequest;
import com.example.travelog.domain.travel.dto.response.TravelListReadResponse;
import com.example.travelog.domain.travel.dto.response.TravelReadResponse;
import com.example.travelog.domain.travel.service.TravelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/travel")
@RequiredArgsConstructor
public class TravelControllerImpl implements TravelController {
    private final TravelService travelService;
    private final S3ImageService s3ImageService;

    @PostMapping()
    public ResponseEntity<?> createTravel(@Valid @RequestBody TravelCreateRequest request,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        travelService.createTravel(request, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{travelId}/thumbnail")
    public String updateThumbnail(@PathVariable Long travelId,
                                  @RequestPart("image") MultipartFile image,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        String imageUrl = s3ImageService.upload(image);
        travelService.updateTravelThumbnail(imageUrl, travelId, userDetails.getUsername());
        return imageUrl;
    }

    @PatchMapping("/{travelId}")
    public ResponseEntity<?> updateTravel(@PathVariable Long travelId,
                                          @RequestBody TravelUpdateRequest request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        travelService.updateTravel(travelId, userDetails.getUsername(), request);
        return ResponseEntity.ok().build();
    }

    // 여행 목록 조회
    @GetMapping()
    public ResponseEntity<List<TravelListReadResponse>> getTravelList(@AuthenticationPrincipal UserDetails userDetails) {
        List<TravelListReadResponse> response = travelService.getTravelList(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    // 여행 상세 조회
    @GetMapping("/{travelId}")
    public ResponseEntity<TravelReadResponse> getTravel(@PathVariable Long travelId,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        TravelReadResponse response = travelService.getTravel(travelId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{travelId}")
    public ResponseEntity<?> deleteTravel(@PathVariable Long travelId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        travelService.deleteTravel(travelId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
