package com.investment.managment.stock;

import com.investment.managment.validation.ValidationHandler;
import com.investment.managment.validation.Validator;
import com.investment.managment.validation.exception.Error;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class StockValidator implements Validator {

    private static final int MAX_SYMBOL_SIZE = 20;
    private final Stock entity;
    private final ValidationHandler handler;

    public static StockValidator from(final Stock entity, final ValidationHandler handler) {
        return new StockValidator(entity, handler);
    }

    private StockValidator(final Stock entity, final ValidationHandler handler) {
        this.handler = requireNonNull(handler);
        this.entity = requireNonNull(entity);
    }


    @Override
    public void validate() {
        checkSymbolConstraint();
    }

    private void checkSymbolConstraint() {
        final var symbol = this.entity.getSymbol();

        if (isNull(symbol)) {
            this.handler.append(new Error("'symbol' should not be null"));
            return;
        }

        if (symbol.isBlank()) {
            this.handler.append(new Error("'symbol' should not be empty"));
            return;
        }

        if (symbol.length() > MAX_SYMBOL_SIZE) {
            this.handler.append(new Error("'symbol' should be between 1 and %s characters".formatted(MAX_SYMBOL_SIZE)));
            return;
        }
    }
}
