package com.investment.managment.validation.handler;

import com.investment.managment.validation.ValidationHandler;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.validation.exception.Error;

public class ThrowableHandler extends ValidationHandler {

    private ThrowableHandler() {
    }

    public static ThrowableHandler newHandler() {
        return new ThrowableHandler();
    }

    @Override
    public ValidationHandler append(final Error error) {
        throw DomainException.of(error);
    }
}
