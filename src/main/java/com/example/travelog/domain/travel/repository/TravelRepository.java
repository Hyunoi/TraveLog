package com.example.travelog.domain.travel.repository;

import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRepository extends JpaRepository <Travel, Long> {
    List<Travel> findAllByUser(User user);
}