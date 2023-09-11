package com.zerobase.fastlms.banner.exception.handler;

import com.zerobase.fastlms.banner.exception.DuplicateImageFileException;
import com.zerobase.fastlms.banner.exception.FailedToConvertImageFileException;
import com.zerobase.fastlms.banner.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class BannerExceptionHandler {

    @ExceptionHandler(FailedToConvertImageFileException.class)
    public ResponseEntity<ErrorResponse> handleFailedToConvertImageFileException
            (FailedToConvertImageFileException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getCode()));

    }

    @ExceptionHandler(DuplicateImageFileException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateImageFileException
            (DuplicateImageFileException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getCode()));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException
            (EntityNotFoundException e) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getCode()));

    }

}
