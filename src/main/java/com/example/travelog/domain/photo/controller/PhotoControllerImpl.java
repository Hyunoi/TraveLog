package com.example.travelog.domain.photo.controller;

import com.example.travelog.domain.photo.dto.request.PhotoCreateRequest;
import com.example.travelog.domain.photo.dto.request.PhotoUpdateRequest;
import com.example.travelog.domain.photo.dto.response.PhotoListReadResponse;
import com.example.travelog.domain.photo.service.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @GetMapping("/{travelId}")
    public ResponseEntity<List<PhotoListReadResponse>> getPhotoList(@PathVariable Long travelId,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        List<PhotoListReadResponse> response = photoService.getPhotoList(travelId, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{photoId}")
    public ResponseEntity<?> updatePhoto(@PathVariable Long photoId,
                                         @RequestPart("request") @Valid PhotoUpdateRequest request,
                                         @RequestPart("image") MultipartFile image,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        photoService.updatePhoto(photoId, request, image, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        photoService.deletePhoto(photoId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
