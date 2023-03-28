package com.investment.managment.execution.update.sell;

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

public class UpdateSellExecutionUseCase extends DoubleUseCase<UpdateExecutionCommandInput, Execution, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;


    public UpdateSellExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public UpdateExecutionCommandOutput execute(final UpdateExecutionCommandInput aCommand, final Execution execution) {
        return updateWithValidation(aCommand, execution);
    }


    private UpdateExecutionCommandOutput updateWithValidation(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        final var originId = anExecution.getOrigin();
        final var buyExecutedQuantity =
                this.executionGateway.findById(originId)
                        .map(Execution::getExecutedQuantity)
                        .orElse(0L);

        final var totalSellExecutedQuantity =
                this.executionGateway.findAllByOriginId(originId)
                        .stream()
                        .filter(execution -> !execution.getId().equals(anExecution.getId()))
                        .map(Execution::getExecutedQuantity)
                        .filter(Objects::nonNull)
                        .reduce(Long::sum)
                        .orElse(0L);

        final var quantityRemaining = buyExecutedQuantity - totalSellExecutedQuantity;
        final var executedQuantity = ofNullable(aCommand.executedQuantity()).orElse(0L);

        if (quantityRemaining < executedQuantity) {
            throw DomainExeceptionFactory.constraintException(new Error("'executedPrice' should not be greater than %s".formatted(quantityRemaining)));
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
                        anExecution.getPnlClose(),
                        aCommand.executedAt()
                );

        return UpdateExecutionCommandOutput.from(this.executionGateway.update(anExecution));
    }
}
