package com.investment.managment.validation.exception;

import com.investment.managment.AggregateRoot;
import com.investment.managment.Identifier;

public final class DomainExeceptionFactory {

    private DomainExeceptionFactory() {
    }


    public static NotFoundException notFoundException(Identifier<?> identifier, Class<? extends AggregateRoot<?>> type) {
        return NotFoundException.of(identifier, type);
    }

    public static ConstraintException constraintException(final Error error) {
        return ConstraintException.of(error);
    }
}
