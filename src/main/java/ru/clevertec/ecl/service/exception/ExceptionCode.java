package ru.clevertec.ecl.service.exception;

public enum ExceptionCode {
    USER_NOT_FOUND(40101),
    NOT_VALID_USER_NAME(40102),

    NEWS_NOT_FOUND(40201),
    NOT_VALID_NEWS(40202),

    COMMENT_NOT_FOUND(40301),
    NOT_VALID_COMMENT(40302);

    private final int errorCode;

    ExceptionCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
