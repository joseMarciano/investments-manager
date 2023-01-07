package com.investment.managment.execution;

import com.investment.managment.validation.ValidationHandler;
import com.investment.managment.validation.Validator;
import com.investment.managment.validation.exception.Error;

import static com.investment.managment.execution.ExecutionStatus.BUY;
import static com.investment.managment.execution.ExecutionStatus.SELL;
import static java.util.Objects.*;

public class ExecutionValidator implements Validator {

    private final Execution entity;
    private final ValidationHandler handler;

    public static ExecutionValidator from(final Execution entity, final ValidationHandler handler) {
        return new ExecutionValidator(entity, handler);
    }

    private ExecutionValidator(final Execution entity, final ValidationHandler handler) {
        this.handler = requireNonNull(handler);
        this.entity = requireNonNull(entity);
    }


    @Override
    public void validate() {
        checkStockIdConstraint();
        checkWalletIdConstraint();
        checkStatusConstraint();
        checkSellExecutedQuantityConstraint();
        checkSellExecutedPriceConstraint();
        checkBuyExecutedQuantityConstraint();
        checkBuyExecutedPriceConstraint();
    }

    private void checkStockIdConstraint() {
        final var stockId = this.entity.getStockId();

        if (isNull(stockId)) {
            this.handler.append(new Error("'stockId' should not be null"));
            return;
        }
    }

    private void checkWalletIdConstraint() {
        final var walletId = this.entity.getWalletId();

        if (isNull(walletId)) {
            this.handler.append(new Error("'walletId' should not be null"));
            return;
        }
    }

    private void checkStatusConstraint() {
        final var status = this.entity.getStatus();

        if (isNull(status)) {
            this.handler.append(new Error("'status' should not be null"));
            return;
        }
    }

    private void checkSellExecutedQuantityConstraint() {
        final var sellExecutedQuantity = this.entity.getSellExecutedQuantity();
        final var status = this.entity.getStatus();

        if (SELL.equals(status) && isNull(sellExecutedQuantity)) {
            this.handler.append(new Error("'sellExecutedQuantity' must not be null"));
            return;
        }

        if (BUY.equals(status) && nonNull(sellExecutedQuantity)) {
            this.handler.append(new Error("'sellExecutedQuantity' should not be filled"));
            return;
        }
    }

    private void checkSellExecutedPriceConstraint() {
        final var sellExecutedPrice = this.entity.getSellExecutedPrice();
        final var status = this.entity.getStatus();

        if (SELL.equals(status) && isNull(sellExecutedPrice)) {
            this.handler.append(new Error("'sellExecutedPrice' must not be null"));
            return;
        }

        if (BUY.equals(status) && nonNull(sellExecutedPrice)) {
            this.handler.append(new Error("'sellExecutedPrice' should not be filled"));
            return;
        }
    }

    private void checkBuyExecutedQuantityConstraint() {
        final var buyExecutedQuantity = this.entity.getBuyExecutedQuantity();
        final var status = this.entity.getStatus();


        if(BUY.equals(status) && isNull(buyExecutedQuantity)) {
            this.handler.append(new Error("'buyExecutedQuantity' must not be null"));
            return;
        }

        if (SELL.equals(status) && nonNull(buyExecutedQuantity)) {
            this.handler.append(new Error("'buyExecutedQuantity' should not be filled"));
            return;
        }
    }

    private void checkBuyExecutedPriceConstraint() {
        final var buyExecutedPrice = this.entity.getBuyExecutedPrice();
        final var status = this.entity.getStatus();

        if (SELL.equals(status) && nonNull(buyExecutedPrice)) {
            this.handler.append(new Error("'buyExecutedPrice' should not be filled"));
            return;
        }

        if (BUY.equals(status) && isNull(buyExecutedPrice)) {
            this.handler.append(new Error("'buyExecutedPrice' must not be null"));
            return;
        }
    }
//
//    private void checkDescriptionConstraint() {
//        final var description = this.entity.getDescription();
//        if (isNull(description)) {
//            return;
//        }
//
//        if (description.length() > MAX_DESCRIPTION_SIZE) {
//            this.handler.append(new Error("'description' should be between 1 and %s characters".formatted(MAX_DESCRIPTION_SIZE)));
//            return;
//        }
//    }
}
