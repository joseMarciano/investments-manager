package com.investment.managment.execution;

import com.investment.managment.validation.ValidationHandler;
import com.investment.managment.validation.Validator;
import com.investment.managment.validation.exception.Error;

import java.math.BigDecimal;

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
        checkOriginIdConstraint();
        checkExecutedQuantityConstraint();
        checkExecutedPriceConstraint();
        checkProfitPercentageConstraint();
        checkExecutedAtConstraint();
    }

    private void checkStockIdConstraint() {
        final var stockId = this.entity.getStockId();

        if (isNull(stockId) || isNull(stockId.getValue())) {
            this.handler.append(new Error("'stockId' must not be null"));
            return;
        }
    }

    private void checkWalletIdConstraint() {
        final var walletId = this.entity.getWalletId();

        if (isNull(walletId) || isNull(walletId.getValue())) {
            this.handler.append(new Error("'walletId' must not be null"));
            return;
        }
    }

    private void checkOriginIdConstraint() {
        final var originId = this.entity.getOrigin();
        final var status = this.entity.getStatus();

        if (SELL.equals(status) && isNull(originId)) {
            this.handler.append(new Error("'originId' must not be null"));
            return;
        }

        if (BUY.equals(status) && nonNull(originId)) {
            this.handler.append(new Error("'originId' should not be filled"));
            return;
        }
    }

    private void checkStatusConstraint() {
        final var status = this.entity.getStatus();

        if (isNull(status)) {
            this.handler.append(new Error("'status' must not be null"));
            return;
        }
    }


    private void checkExecutedQuantityConstraint() {
        final var executedQuantity = this.entity.getExecutedQuantity();

        if (isNull(executedQuantity)) {
            this.handler.append(new Error("'executedQuantity' must not be null"));
            return;
        }

        if (executedQuantity.compareTo(0L) <= 0) {
            this.handler.append(new Error("'executedQuantity' should be bigger than 0.0"));
            return;
        }
    }

    private void checkExecutedPriceConstraint() {
        final var executedPrice = this.entity.getExecutedPrice();

        if (isNull(executedPrice)) {
            this.handler.append(new Error("'executedPrice' must not be null"));
            return;
        }

        if (executedPrice.compareTo(BigDecimal.ZERO) <= 0) {
            this.handler.append(new Error("'executedPrice' should be bigger than 0.0"));
            return;
        }
    }

    private void checkProfitPercentageConstraint() {
        final var profitPercentage = this.entity.getProfitPercentage();

        if (isNull(profitPercentage)) {
            this.handler.append(new Error("'profitPercentage' must not be null"));
            return;
        }

        if (profitPercentage.compareTo(0.0) <= 0) {
            this.handler.append(new Error("'profitPercentage' should be bigger than 0.0"));
            return;
        }
    }

    public void checkExecutedAtConstraint() {
        final var executedAt = this.entity.getExecutedAt();

        if (isNull(executedAt)) {
            this.handler.append(new Error("'executedAt' must not be null"));
            return;
        }
    }
}
