package com.example.travelog.domain.travel.service;

import com.example.travelog.domain.travel.dto.request.TravelCreateRequest;
import com.example.travelog.domain.travel.dto.request.TravelUpdateRequest;
import com.example.travelog.domain.travel.dto.response.TravelListReadResponse;
import com.example.travelog.domain.travel.dto.response.TravelReadResponse;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {
    private final TravelRepository travelRepository;
    private final EntityValidator entityValidator;

    public void createTravel(TravelCreateRequest request, String email) {
        User user = entityValidator.validateUserByEmail(email);

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
        User user = entityValidator.validateUserByEmail(email);
        Travel travel = entityValidator.validateTravelById(travelId);

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        if (imageUrl != null) travel.updateThumbnailImage(imageUrl);
        travelRepository.save(travel);
    }

    @Transactional
    public void updateTravel(Long travelId, String email, TravelUpdateRequest request) {
        User user = entityValidator.validateUserByEmail(email);
        Travel travel = entityValidator.validateTravelById(travelId);

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

    public List<TravelListReadResponse> getTravelList(String email) {
        User user = entityValidator.validateUserByEmail(email);

        List<Travel> travelList = travelRepository.findAllByUser(user);

        return travelList.stream()
                .map(travel -> new TravelListReadResponse(
                        travel.getTitle(),
                        travel.getLocation(),
                        travel.getThumbnailUrl()
                )).toList();
    }

    public TravelReadResponse getTravel(Long travelId, String email) {
        User user = entityValidator.validateUserByEmail(email);
        Travel travel = entityValidator.validateTravelById(travelId);

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        return new TravelReadResponse(
                travel.getTitle(),
                travel.getDescription(),
                travel.getLocation(),
                travel.getThumbnailUrl()
        );
    }

    public void deleteTravel(Long travelId, String email) {
        User user = entityValidator.validateUserByEmail(email);
        Travel travel = entityValidator.validateTravelById(travelId);

        if (!travel.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }

        travelRepository.delete(travel);
    }
}