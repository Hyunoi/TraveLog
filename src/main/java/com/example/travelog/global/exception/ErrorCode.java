package com.example.travelog.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // User
    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "해당 이메일의 유저가 이미 존재합니다."),
    ALREADY_EXIST_NICKNAME(HttpStatus.CONFLICT, "해당 닉네임의 유저가 이미 존재합니다."),
    NOT_FOUNT_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    FORBIDDEN_USER(HttpStatus.FORBIDDEN, "해당 유저에게 권한이 없습니다."),

    // S3
    EMPTY_FILE(HttpStatus.BAD_REQUEST, "업로드할 파일이 비어 있습니다."),
    INVALID_FILE_EXTENTION(HttpStatus.BAD_REQUEST, "허용되지 않은 파일 확장자입니다. jpg, jpeg, png 파일만 업로드 가능합니다."),
    NO_FILE_EXTENTION(HttpStatus.BAD_REQUEST, "파일 확장자가 없습니다."),
    IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드 중 오류가 발생했습니다."),
    IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 삭제 중 오류가 발생했습니다."),
    PUT_OBJECT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S3에 이미지 업로드 실패했습니다."),

    // Travel
    NOT_FOUNT_TRAVEL(HttpStatus.NOT_FOUND, "해당 여행을 찾을 수 없습니다.");


    private final HttpStatus errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}