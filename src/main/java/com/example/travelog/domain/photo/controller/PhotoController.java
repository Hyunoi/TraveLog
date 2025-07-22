package com.example.travelog.domain.photo.controller;

import com.example.travelog.domain.photo.dto.request.PhotoCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "PHOTO")
public interface PhotoController {
    @Operation(summary = "사진 생성")
    public ResponseEntity<?> createPhoto(@Parameter(description = "코멘트, 장소(선택) 기입")
                                         @RequestPart("request") @Valid PhotoCreateRequest request,
                                         @PathVariable Long travelId,
                                         @Parameter(description = "이미지 첨부")
                                         @RequestPart("image") MultipartFile image,
                                         @AuthenticationPrincipal UserDetails userDetails);
}
