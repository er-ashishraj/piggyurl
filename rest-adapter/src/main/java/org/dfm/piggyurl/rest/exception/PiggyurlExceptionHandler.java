package org.dfm.piggyurl.rest.exception;

import org.dfm.piggyurl.domain.exception.PiggyurlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice(basePackages = {"org.dfm.piggyurl"})
public class PiggyurlExceptionHandler {

    @ExceptionHandler(value = PiggyurlNotFoundException.class)
    public final ResponseEntity<PiggyurlExceptionResponse> handlePiggyurlNotFoundException(
            final Exception exception, final WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                PiggyurlExceptionResponse.builder().message(exception.getMessage())
                        .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
    }
}
