package com.investment.managment.wallet;

import com.investment.managment.validation.ValidationHandler;
import com.investment.managment.validation.Validator;
import com.investment.managment.validation.exception.Error;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class WalletValidator implements Validator {

    private final Wallet entity;
    private final ValidationHandler handler;

    public static WalletValidator from(final Wallet entity, final ValidationHandler handler) {
        return new WalletValidator(entity, handler);
    }

    private WalletValidator(final Wallet entity, final ValidationHandler handler) {
        this.handler = requireNonNull(handler);
        this.entity = requireNonNull(entity);
    }


    @Override
    public void validate() {
        checkNameConstraint();
    }

    private void checkNameConstraint() {
        final var name = this.entity.getName();

        if (isNull(name)) {
            this.handler.append(new Error("'name' should not be null"));
            return;
        }

        if(name.isBlank()) {
            this.handler.append(new Error("'name' should not be empty"));
            return;
        }
    }
}
