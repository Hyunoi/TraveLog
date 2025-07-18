package com.example.travelog.domain.travel.service;

import com.example.travelog.domain.travel.dto.request.TravelCreateRequest;
import com.example.travelog.domain.travel.dto.request.TravelUpdateRequest;
import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.domain.travel.repository.TravelRepository;
import com.example.travelog.domain.user.entity.User;
import com.example.travelog.domain.user.repository.UserRepository;
import com.example.travelog.global.exception.CustomException;
import com.example.travelog.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;

    public void createTravel(TravelCreateRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        Travel travel = Travel.builder()
                .user(user)
                .title(request.title())
                .description(request.description())
                .location(request.location())
                .startTime(request.startDate())
                .endTime(request.endDate())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();

        travelRepository.save(travel);
    }

    public void updateTravelThumbnail(String imageUrl, Long travelId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_TRAVEL));

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        if (imageUrl != null) travel.updateThumbnailImage(imageUrl);
        travelRepository.save(travel);
    }

    @Transactional
    public void updateTravel(Long travelId, String email, TravelUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        Travel travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_TRAVEL));

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        travel.updateTravel(
                request.title(),
                request.description(),
                request.startDate(),
                request.endDate()
        );
    }
}