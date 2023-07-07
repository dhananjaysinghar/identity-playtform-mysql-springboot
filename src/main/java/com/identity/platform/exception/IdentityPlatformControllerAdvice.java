package com.identity.platform.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class IdentityPlatformControllerAdvice {

    @ExceptionHandler(IdentityPlatformException.class)
    public ResponseEntity<ErrorResponseDto> handleException(IdentityPlatformException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .status(exception.getHttpStatus().toString())
                .timeStamp(exception.getTimeStamp())
                .build();
        log.error("Error occurred: {}", errorResponseDto);
        return new ResponseEntity<>(errorResponseDto, exception.getHttpStatus());
    }
}
