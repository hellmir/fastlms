package com.zerobase.fastlms.banner.exception.handler;

import com.zerobase.fastlms.banner.exception.DuplicateImageFileException;
import com.zerobase.fastlms.banner.exception.FailedToConvertImageFileException;
import com.zerobase.fastlms.banner.exception.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class BannerExceptionHandler {

    Logger log = LoggerFactory.getLogger(BannerExceptionHandler.class);

    @ExceptionHandler(FailedToConvertImageFileException.class)
    public ResponseEntity<ErrorResponse> handleFailedToConvertImageFileException
            (FailedToConvertImageFileException e) {

        log.error("Error exception occurred: {}", e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getCode()));

    }

    @ExceptionHandler(DuplicateImageFileException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateImageFileException
            (DuplicateImageFileException e) {

        log.info("Exception occurred: {}", e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getCode()));

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException
            (EntityNotFoundException e) {

        log.warn("Warning exception occurred: {}", e.getMessage(), e);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorResponse.getCode()));

    }

}
