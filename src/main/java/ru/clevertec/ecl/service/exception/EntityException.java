package ru.clevertec.ecl.service.exception;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * EntityException class for providing entire exceptions with error code and time
 */
@Getter
public class EntityException extends RuntimeException {
    private final String timeStamp;
    private final int errorCode;

    public EntityException(int errorCode) {
        this.errorCode = errorCode;
        timeStamp = LocalDateTime.now().toString();
    }
}
