package com.investment.managment.execution.update.buy;

import com.investment.managment.DoubleUseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.execution.update.UpdateExecutionCommandOutput;
import com.investment.managment.validation.exception.DomainExeceptionFactory;
import com.investment.managment.validation.exception.Error;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class UpdateBuyExecutionUseCase extends DoubleUseCase<UpdateExecutionCommandInput, Execution, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;


    public UpdateBuyExecutionUseCase(final ExecutionGateway executionGateway) {
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
                        .map(Execution::getExecutedQuantity)
                        .filter(Objects::nonNull)
                        .reduce(Long::sum)
                        .orElse(0L);

        final var executedQuantity = ofNullable(aCommand.executedQuantity()).orElse(0L);

        if (executedQuantity < totalQuantityExecutionsSold) {
            throw DomainExeceptionFactory.constraintException(new Error("'executedPrice' can not be less than %s".formatted(totalQuantityExecutionsSold)));
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
                        anExecution.getStatus(),
                        anExecution.getPnlOpen(),
                        anExecution.getPnlOpenPercentage(),
                        anExecution.getPnlClose(),
                        anExecution.getPnlClosePercentage(),
                        aCommand.executedAt()
                );

        return UpdateExecutionCommandOutput.from(this.executionGateway.update(anExecution));
    }


}
