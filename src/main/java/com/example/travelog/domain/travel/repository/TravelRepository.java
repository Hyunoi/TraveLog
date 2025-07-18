package com.example.travelog.domain.travel.repository;

import com.example.travelog.domain.travel.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRepository extends JpaRepository <Travel, Long> {

}