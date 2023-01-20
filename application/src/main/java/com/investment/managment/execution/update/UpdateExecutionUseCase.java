package com.investment.managment.execution.update;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.update.buy.UpdateBuyExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellExecutionUseCase;

import java.util.function.Function;

import static com.investment.managment.execution.ExecutionStatus.SELL;
import static com.investment.managment.validation.exception.DomainExeceptionFactory.notFoundException;
import static java.util.Objects.requireNonNull;

public class UpdateExecutionUseCase extends UseCase<UpdateExecutionCommandInput, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    private final UpdateBuyExecutionUseCase updateBuyExecutionUseCase;

    private final UpdateSellExecutionUseCase updateSellExecutionUseCase;


    public UpdateExecutionUseCase(final ExecutionGateway executionGateway,
                                  final UpdateBuyExecutionUseCase updateBuyExecutionUseCase,
                                  final UpdateSellExecutionUseCase updateSellExecutionUseCase) {
        this.executionGateway = requireNonNull(executionGateway);
        this.updateBuyExecutionUseCase = updateBuyExecutionUseCase;
        this.updateSellExecutionUseCase = updateSellExecutionUseCase;
    }

    @Override
    public UpdateExecutionCommandOutput execute(final UpdateExecutionCommandInput aCommand) {
        final var executionID = ExecutionID.from(aCommand.id());

        return this.executionGateway.findById(executionID)
                .map(resolve(aCommand))
                .orElseThrow(() -> notFoundException(executionID, Execution.class));
    }

    private Function<Execution, UpdateExecutionCommandOutput> resolve(final UpdateExecutionCommandInput aCommand) {
        return anExecution -> {
            if (SELL.equals(anExecution.getStatus()))
                return this.updateSellExecutionUseCase.execute(aCommand, anExecution);
            return this.updateBuyExecutionUseCase.execute(aCommand, anExecution);
        };
    }
}
