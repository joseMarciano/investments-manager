package com.investment.managment.execution.update.buy;

import com.investment.managment.DoubleUseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.execution.update.UpdateExecutionCommandOutput;
import com.investment.managment.validation.exception.DomainExeceptionFactory;
import com.investment.managment.validation.exception.Error;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class UpdateBuyFieldsExecutionUseCase extends DoubleUseCase<UpdateExecutionCommandInput, Execution, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;


    public UpdateBuyFieldsExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public UpdateExecutionCommandOutput execute(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        if (!this.executionGateway.existsByOriginId(anExecution.getId())) return update(aCommand, anExecution);

        return updateWithValidation(aCommand, anExecution);
    }


    private UpdateExecutionCommandOutput updateWithValidation(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        final var totalQuantityExecutionsSold =
                this.executionGateway.findAllByOriginId(anExecution.getId())
                        .stream()
                        .map(Execution::getSellExecutedQuantity)
                        .filter(Objects::nonNull)
                        .reduce(Long::sum)
                        .orElse(0L);

        final var buyExecutedQuantity = ofNullable(aCommand.executedQuantity()).orElse(0L);

        if (buyExecutedQuantity < totalQuantityExecutionsSold) {
            throw DomainExeceptionFactory.constraintException(new Error("'buyExecutedPrice' can not be less than %s".formatted(totalQuantityExecutionsSold)));
        }

        return update(aCommand, anExecution);
    }

    private UpdateExecutionCommandOutput update(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        anExecution
                .update(
                        anExecution.getOrigin(),
                        anExecution.getStockId(),
                        anExecution.getWalletId(),
                        aCommand.profitPercentage(),
                        aCommand.executedQuantity(),
                        aCommand.executedPrice(),
                        anExecution.getSellExecutedQuantity(),
                        anExecution.getSellExecutedPrice(),
                        anExecution.getStatus(),
                        aCommand.executedAt(),
                        anExecution.getSoldAt()
                );

        return UpdateExecutionCommandOutput.from(this.executionGateway.update(anExecution));
    }


}
