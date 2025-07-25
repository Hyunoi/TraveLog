package com.example.travelog.domain.photo.service;

import com.example.travelog.domain.photo.dto.request.PhotoCreateRequest;
import com.example.travelog.domain.photo.dto.request.PhotoUpdateRequest;
import com.example.travelog.domain.photo.dto.response.PhotoListReadResponse;
import com.example.travelog.domain.photo.entity.Photo;
import com.example.travelog.domain.photo.repository.PhotoRepository;
import com.example.travelog.global.s3.service.S3ImageService;
import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.domain.travel.repository.TravelRepository;
import com.example.travelog.domain.user.entity.User;
import com.example.travelog.domain.user.repository.UserRepository;
import com.example.travelog.global.exception.CustomException;
import com.example.travelog.global.exception.ErrorCode;
import com.example.travelog.global.validator.EntityValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final S3ImageService s3ImageService;
    private final PhotoRepository photoRepository;
    private final EntityValidator entityValidator;

    @Transactional
    public void createPhoto(String comment,
                            String location,
                            Long travelId,
                            MultipartFile image,
                            String email) {
        User user = entityValidator.validateUserByEmail(email);
        Travel travel = entityValidator.validateTravelById(travelId);
        entityValidator.validateUserMatch(travel.getUser().getId(), user.getId());

        String photoUrl = s3ImageService.upload(image);
        Photo photo = Photo.builder()
                .travel(travel)
                .photoUrl(photoUrl)
                .location(location)
                .comment(comment)
                .build();

        photoRepository.save(photo);
    }

    public List<PhotoListReadResponse> getPhotoList(Long travelId, String email) {
        User user = entityValidator.validateUserByEmail(email);
        Travel travel = entityValidator.validateTravelById(travelId);
        entityValidator.validateUserMatch(travel.getUser().getId(), user.getId());

        List<Photo> photoList = photoRepository.findAllByTravel(travel);
        return photoList.stream()
                .map(photo -> new PhotoListReadResponse(
                        photo.getId(),
                        photo.getPhotoUrl(),
                        photo.getComment(),
                        photo.getLocation(),
                        photo.getCreatedAt()
                )).toList();
    }

    @Transactional
    public void updatePhoto(Long photoId,
                            PhotoUpdateRequest request,
                            MultipartFile image,
                            String email) {
        User user = entityValidator.validateUserByEmail(email);
        Photo photo = entityValidator.validatePhotoById(photoId);
        entityValidator.validateUserMatch(photo.getTravel().getUser().getId(), user.getId());

        String photoUrl = s3ImageService.upload(image);
        photo.updatePhoto(
                request.comment(),
                photoUrl,
                request.location()
        );
    }

    public void deletePhoto(Long photoId, String email) {
        User user = entityValidator.validateUserByEmail(email);
        Photo photo = entityValidator.validatePhotoById(photoId);
        entityValidator.validateUserMatch(photo.getTravel().getUser().getId(), user.getId());
        photoRepository.delete(photo);
    }
}
