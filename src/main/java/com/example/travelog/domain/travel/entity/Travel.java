package com.example.travelog.domain.travel.entity;

import com.example.travelog.domain.user.entity.User;
import com.example.travelog.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Travel extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String description;
    private String locaiton;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double latitude;
    private double longitude;
}
