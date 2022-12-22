package com.investment.managment.api;

import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.validation.exception.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DomainException.class})
    public ResponseEntity<?> domainException(final DomainException domainException) {
        return ResponseEntity.unprocessableEntity()
                .body(ApiError.from(domainException));
    }


    private record ApiError(String message, List<Error> errors) {
        public static ApiError from(final DomainException ex) {
            return new ApiError(ex.getMessage(), List.of(ex.getError()));
        }
    }


}
