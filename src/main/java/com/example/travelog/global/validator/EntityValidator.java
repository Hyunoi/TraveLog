package com.example.travelog.global.validator;

import com.example.travelog.domain.photo.entity.Photo;
import com.example.travelog.domain.photo.repository.PhotoRepository;
import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.domain.travel.repository.TravelRepository;
import com.example.travelog.domain.user.entity.User;
import com.example.travelog.domain.user.repository.UserRepository;
import com.example.travelog.global.exception.CustomException;
import com.example.travelog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityValidator {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final PhotoRepository photoRepository;

    public User validateUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public Travel validateTravelById(Long travelId) {
        return travelRepository.findById(travelId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_TRAVEL));
    }

    public Photo validatePhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PHOTO));
    }

    public void validateUserMatch(Long ownerId, Long requesterId) {
        if (!ownerId.equals(requesterId)) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }
    }
}
