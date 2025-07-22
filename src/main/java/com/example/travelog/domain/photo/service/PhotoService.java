package com.example.travelog.domain.photo.service;

import com.example.travelog.domain.photo.dto.request.PhotoCreateRequest;
import com.example.travelog.domain.photo.dto.request.PhotoUpdateRequest;
import com.example.travelog.domain.photo.dto.response.PhotoListReadResponse;
import com.example.travelog.domain.photo.entity.Photo;
import com.example.travelog.domain.photo.repository.PhotoRepository;
import com.example.travelog.domain.s3.service.S3ImageService;
import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.domain.travel.repository.TravelRepository;
import com.example.travelog.domain.user.entity.User;
import com.example.travelog.domain.user.repository.UserRepository;
import com.example.travelog.global.exception.CustomException;
import com.example.travelog.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final S3ImageService s3ImageService;
    private final PhotoRepository photoRepository;

    @Transactional
    public void createPhoto(PhotoCreateRequest request,
                            Long travelId,
                            MultipartFile image,
                            String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_TRAVEL));

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        String photoUrl = s3ImageService.upload(image);

        Photo photo = Photo.builder()
                .travel(travel)
                .photoUrl(photoUrl)
                .location(request.location())
                .comment(request.comment())
                .build();

        photoRepository.save(photo);
    }

    public List<PhotoListReadResponse> getPhotoList(Long travelId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_TRAVEL));

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        List<Photo> photoList = photoRepository.findAllByTravel(travel);

        return photoList.stream()
                .map(photo -> new PhotoListReadResponse(
                        photo.getId(),
                        photo.getComment(),
                        photo.getPhotoUrl(),
                        photo.getLocation(),
                        photo.getCreatedAt()
                )).toList();
    }

    @Transactional
    public void updatePhoto(Long photoId,
                            PhotoUpdateRequest request,
                            MultipartFile image,
                            String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PHOTO));

        if (!photo.getTravel().getUser().getEmail().equals(email)) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        String photoUrl = s3ImageService.upload(image);

        photo.updatePhoto(
                request.comment(),
                photoUrl,
                request.location()
        );
    }
}
