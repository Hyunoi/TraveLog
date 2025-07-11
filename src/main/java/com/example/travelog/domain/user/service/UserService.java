package com.example.travelog.domain.user.service;

import com.example.travelog.domain.user.dto.request.UserSignUpRequest;
import com.example.travelog.domain.user.entity.Role;
import com.example.travelog.domain.user.entity.User;
import com.example.travelog.domain.user.repository.UserRepository;
import com.example.travelog.global.exception.CustomException;
import com.example.travelog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpRequest request) {
        if (userRepository.existsUserByEmail(request.email()))
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL);

        if (userRepository.existsUserByNickname(request.nickname()))
            throw new CustomException(ErrorCode.ALREADY_EXIST_NICKNAME);

        String encodePassword = passwordEncoder.encode(request.password());

        User user = new User(request.email(),
                encodePassword,
                request.nickname(),
                Role.USER);

        userRepository.save(user);
    }
}
