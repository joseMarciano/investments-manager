package com.investment.managment.execution.sell;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.validation.exception.DomainExeceptionFactory;
import com.investment.managment.validation.exception.Error;

import java.util.Objects;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class SellExecutionUseCase extends UseCase<SellExecutionCommandInput, SellExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    public SellExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = requireNonNull(executionGateway);
    }

    @Override
    public SellExecutionCommandOutput execute(final SellExecutionCommandInput aCommand) {
        final var originId = aCommand.originId();
        return this.executionGateway.findById(originId)
                .map(resolve(aCommand))
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(originId, Execution.class));
    }

    private Function<Execution, SellExecutionCommandOutput> resolve(final SellExecutionCommandInput aCommand) {
        return originExecution -> {
            final var executionSold = ExecutionBuilder.create()
                    .status(ExecutionStatus.SELL)
                    .executedQuantity(aCommand.executedQuantity())
                    .executedPrice(aCommand.executedPrice())
                    .executedAt(aCommand.executedAt())
                    .profitPercentage(originExecution.getProfitPercentage())
                    .stockId(originExecution.getStockId())
                    .walletId(originExecution.getWalletId())
                    .origin(originExecution.getId())
                    .build();

            validate(originExecution, aCommand);

            return SellExecutionCommandOutput.from(this.executionGateway.create(executionSold));
        };
    }

    private void validate(final Execution originExecution, final SellExecutionCommandInput aCommand) {
        final var totalExecutedQuantity = originExecution.getExecutedQuantity();
        final var currentExecutionsSold = this.executionGateway.findAllByOriginId(originExecution.getId()) // TODO: generify validators in application layer
                .stream()
                .map(Execution::getExecutedQuantity)
                .filter(Objects::nonNull)
                .reduce(Long::sum)
                .orElse(0L);
        final var quantityRemaining = totalExecutedQuantity - currentExecutionsSold;

        if (quantityRemaining < aCommand.executedQuantity()) {
            throw DomainExeceptionFactory.constraintException(new Error("'executedQuantity' can not be greater than %s".formatted(quantityRemaining)));
        }

    }
}
