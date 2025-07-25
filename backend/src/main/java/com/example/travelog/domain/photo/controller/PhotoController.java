package com.example.travelog.domain.photo.controller;

import com.example.travelog.domain.photo.dto.request.PhotoCreateRequest;
import com.example.travelog.domain.photo.dto.request.PhotoUpdateRequest;
import com.example.travelog.domain.photo.dto.response.PhotoListReadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "PHOTO")
public interface PhotoController {
    @Operation(summary = "사진 생성")
    public ResponseEntity<?> createPhoto(@PathVariable Long travelId,
                                         @RequestParam("comment") String comment,
                                         @RequestParam("location") String location,
                                         @RequestParam("image") MultipartFile image,
                                         @AuthenticationPrincipal UserDetails userDetails);


    @Operation(summary = "사진 리스트 조회")
    public ResponseEntity<List<PhotoListReadResponse>> getPhotoList(@PathVariable Long travelId,
                                                                    @AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "사진 수정")
    public ResponseEntity<?> updatePhoto(@PathVariable Long photoId,
                                         @RequestPart("request") @Valid PhotoUpdateRequest request,
                                         @RequestPart("image") MultipartFile image,
                                         @AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "사진 삭제")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId,
                                         @AuthenticationPrincipal UserDetails userDetails);
}
