package com.example.travelog.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // User
    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "해당 이메일의 유저가 이미 존재합니다."),
    ALREADY_EXIST_NICKNAME(HttpStatus.CONFLICT, "해당 닉네임의 유저가 이미 존재합니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}