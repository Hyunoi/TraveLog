package com.example.travelog.domain.photo.controller;

import com.example.travelog.domain.photo.dto.request.PhotoCreateRequest;
import com.example.travelog.domain.photo.service.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/photo")
@RequiredArgsConstructor
public class PhotoControllerImpl implements PhotoController {
    private final PhotoService photoService;

    @PostMapping(value = "/{travelId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPhoto(@RequestPart("request") @Valid PhotoCreateRequest request,
                                         @PathVariable Long travelId,
                                         @RequestPart("image") MultipartFile image,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        photoService.createPhoto(request, travelId, image, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
