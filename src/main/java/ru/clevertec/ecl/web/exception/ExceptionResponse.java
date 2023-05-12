package ru.clevertec.ecl.web.exception;

public class ExceptionResponse {
    private final String errorMessage;
    private final int errorCode;
    public final String timeStamp;

    public ExceptionResponse(String errorMessage, int errorCode, String timeStamp) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
