package com.investment.managment.validation;

import com.investment.managment.validation.exception.Error;

public abstract class ValidationHandler {
    public abstract ValidationHandler append(Error error);
}
