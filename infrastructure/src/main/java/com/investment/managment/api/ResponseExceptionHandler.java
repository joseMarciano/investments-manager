package com.investment.managment.api;

import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.validation.exception.Error;
import com.investment.managment.validation.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> internalServerError(final Exception ex, final WebRequest webRequest) {
        return handleExceptionInternal(ex, new ApiError("Internal server error",
                List.of(new Error(ex.getMessage()))), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, webRequest);
    }

    @ExceptionHandler(value = {DomainException.class})
    public ResponseEntity<?> domainException(final DomainException domainException) {
        return ResponseEntity.unprocessableEntity()
                .body(ApiError.from(domainException));
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<?> notFoundExpcetion(final NotFoundException domainException) {
        return ResponseEntity.notFound()
                .build();
    }


    private record ApiError(String message, List<Error> errors) {
        public static ApiError from(final DomainException ex) {
            return new ApiError(ex.getMessage(), List.of(ex.getError()));
        }
    }


}
