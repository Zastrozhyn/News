package ru.clevertec.ecl.service.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EntityException extends RuntimeException {
    private final String timeStamp;
    private final int errorCode;

    public EntityException(int errorCode) {
        this.errorCode = errorCode;
        timeStamp = LocalDateTime.now().toString();
    }
}
