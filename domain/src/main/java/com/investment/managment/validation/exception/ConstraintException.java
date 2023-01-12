package com.investment.managment.validation.exception;

import java.util.List;

public class ConstraintException extends DomainException {
    private ConstraintException(final Error anError) {
        super(List.of(anError));

    }

    public static ConstraintException of(final Error error) {
        return new ConstraintException(error);
    }
}
