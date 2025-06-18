package com.example.travelog.domain.user.entity;

import com.example.travelog.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String nickname;
}
