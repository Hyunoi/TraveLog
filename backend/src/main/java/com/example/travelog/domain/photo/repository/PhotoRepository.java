package com.example.travelog.domain.photo.repository;

import com.example.travelog.domain.photo.entity.Photo;
import com.example.travelog.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByTravel(Travel travel);
}
