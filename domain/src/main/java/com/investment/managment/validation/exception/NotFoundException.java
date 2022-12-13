package com.investment.managment.validation.exception;

import com.investment.managment.AggregateRoot;
import com.investment.managment.Identifier;

import java.util.List;

public class NotFoundException extends DomainException {
    private NotFoundException(final Error anError) {
        super(List.of(anError));

    }

    public static NotFoundException of(final Identifier<?> anId, final Class<? extends AggregateRoot<?>> aClass) {
        return new NotFoundException(new Error("Entity %s with identifier %s was not found".formatted(anId.getValue(), aClass.getSimpleName())));
    }
}
