package com.michael.libertybank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @JsonIgnore
    private int status;

    private String message;

    private String path;

    private String errorCode;

    private String infoLink;

    private Object details;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    public ApiError(final int status, final String message, final String path, final ErrorCode errorCode) {
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errorCode = (errorCode != null) ? errorCode.toString() : null;
    }


    public ApiError(final int status, final String message, final String path, final String errorCode) {
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
    }


    public ApiError(final int status, final String message, final String path) {
        timeStamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }


    public ApiError(final String message, final Object details) {
        timeStamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
    }

    public ApiError(final String message, final Object details, final String errorCode) {
        timeStamp = LocalDateTime.now();
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }
}
