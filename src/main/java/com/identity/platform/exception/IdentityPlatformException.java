package com.identity.platform.exception;

import java.time.Instant;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IdentityPlatformException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;
    private final Instant timeStamp;

    public IdentityPlatformException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = Instant.now();
    }
}
