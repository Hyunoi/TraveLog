package com.example.travelog.domain.user.service;

import com.example.travelog.domain.user.dto.request.UserLogInRequest;
import com.example.travelog.domain.user.dto.request.UserSignUpRequest;
import com.example.travelog.domain.user.dto.response.UserLoginResponse;
import com.example.travelog.domain.user.entity.Role;
import com.example.travelog.domain.user.entity.User;
import com.example.travelog.domain.user.repository.UserRepository;
import com.example.travelog.global.exception.CustomException;
import com.example.travelog.global.exception.ErrorCode;
import com.example.travelog.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRedisService userRedisService;

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

    public UserLoginResponse logIn(UserLogInRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUNT_USER));

        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new CustomException(ErrorCode.INVALID_PASSWORD);

        String accessToken = jwtTokenProvider.createAccessToken(request.email());
        String refreshToken = jwtTokenProvider.createRefreshToken(request.email());

        userRedisService.setRefreshToken(request.email(), refreshToken);

        return new UserLoginResponse(accessToken, refreshToken);

    }
}
