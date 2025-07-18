package com.example.travelog.domain.travel.controller;

import com.example.travelog.domain.s3.service.S3ImageService;
import com.example.travelog.domain.travel.dto.request.TravelCreateRequest;
import com.example.travelog.domain.travel.dto.request.TravelUpdateRequest;
import com.example.travelog.domain.travel.service.TravelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/travel")
@RequiredArgsConstructor
public class TravelControllerImpl implements TravelController {
    private final TravelService travelService;
    private final S3ImageService s3ImageService;

    @PostMapping()
    public void createTravel(@Valid @RequestBody TravelCreateRequest request,
                             @AuthenticationPrincipal UserDetails userDetails) {
        travelService.createTravel(request, userDetails.getUsername());

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
}
