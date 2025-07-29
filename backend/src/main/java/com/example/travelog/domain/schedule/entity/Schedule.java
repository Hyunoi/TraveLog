package com.example.travelog.domain.schedule.entity;

import com.example.travelog.domain.travel.entity.Travel;
import com.example.travelog.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

    private String description;
    private String place;
    private LocalDateTime scheduledAt;
}
