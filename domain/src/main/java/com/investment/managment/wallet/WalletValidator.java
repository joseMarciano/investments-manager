package com.investment.managment.wallet;

import com.investment.managment.validation.ValidationHandler;
import com.investment.managment.validation.Validator;
import com.investment.managment.validation.exception.Error;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class WalletValidator implements Validator {

    private static final int MAX_NAME_SIZE = 100;
    private static final int MAX_DESCRIPTION_SIZE = 255;
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
        checkDescriptionConstraint();
    }

    private void checkNameConstraint() {
        final var name = this.entity.getName();

        if (isNull(name)) {
            this.handler.append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            this.handler.append(new Error("'name' should not be empty"));
            return;
        }

        if (name.length() > MAX_NAME_SIZE) {
            this.handler.append(new Error("'name' should be between 1 and %s characters".formatted(MAX_NAME_SIZE)));
            return;
        }
    }

    private void checkDescriptionConstraint() {
        final var description = this.entity.getDescription();
        if (isNull(description)) {
            return;
        }

        if (description.length() > MAX_DESCRIPTION_SIZE) {
            this.handler.append(new Error("'description' should be between 1 and %s characters".formatted(MAX_DESCRIPTION_SIZE)));
            return;
        }
    }
}
