package ru.clevertec.ecl.web.exception;

import ru.clevertec.ecl.service.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;

/**
 * Controller for managing exceptions
 */
@ControllerAdvice
public class ExceptionControllerAdviser {
    private static final List<String> AVAILABLE_LOCALES = List.of("en_US", "ru_RU");
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private final ResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public ExceptionControllerAdviser(ResourceBundleMessageSource messages) {
        this.bundleMessageSource = messages;
    }

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityException e, Locale locale) {
        return buildErrorResponse(resolveResourceBundle(getMessageByCode(e.getErrorCode()), locale), e.getErrorCode(), e.getTimeStamp());
    }

    private String resolveResourceBundle(String key, Locale locale) {
        if (!AVAILABLE_LOCALES.contains(locale.toString())) {
            locale = DEFAULT_LOCALE;
        }
        return bundleMessageSource.getMessage(key, null, locale);
    }

    private ResponseEntity<ExceptionResponse> buildErrorResponse(String message, Integer code, String timeStamp) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, code, timeStamp);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private String getMessageByCode(int errorCode) {
        return "error_msg." + errorCode;
    }
}
