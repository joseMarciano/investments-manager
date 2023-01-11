package com.investment.managment.execution.update;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;

import static com.investment.managment.execution.ExecutionStatus.BUY;
import static com.investment.managment.execution.ExecutionStatus.SELL;
import static com.investment.managment.validation.exception.DomainExeceptionFactory.notFoundException;
import static java.util.Objects.requireNonNull;

public class UpdateExecutionUseCase extends UseCase<UpdateExecutionCommandInput, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    private final StockGateway stockGateway;


    public UpdateExecutionUseCase(final ExecutionGateway executionGateway,
                                  final StockGateway stockGateway) {
        this.executionGateway = requireNonNull(executionGateway);
        this.stockGateway = requireNonNull(stockGateway);
    }

    @Override
    public UpdateExecutionCommandOutput execute(final UpdateExecutionCommandInput aCommand) { // TODO: NEED TO BE TESTED
        final var stockId = StockID.from(aCommand.stockId());
        final var executionID = ExecutionID.from(aCommand.id());

        this.stockGateway.findById(stockId)
                .orElseThrow(() -> notFoundException(stockId, Stock.class));
        final var execution = this.executionGateway.findById(executionID)
                .orElseThrow(() -> notFoundException(executionID, Execution.class));

        final var executionBuilder = ExecutionBuilder.from(execution);

        if (BUY.equals(execution.getStatus())) {
            updateBUYFields(aCommand, executionBuilder);
        }

        if (SELL.equals(execution.getStatus())) {
            updateBUYFields(aCommand, executionBuilder);
        }

        executionBuilder
                .stockId(stockId)
                .profitPercentage(aCommand.profitPercentage());

        return UpdateExecutionCommandOutput.from(executionGateway.create(executionBuilder.build()));
    }


    private void updateBUYFields(final UpdateExecutionCommandInput aCommand, final ExecutionBuilder executionBuilder) {
        executionBuilder
                .buyExecutedQuantity(aCommand.executedQuantity())
                .buyExecutedPrice(aCommand.executedPrice())
                .boughtAt(aCommand.executedAt());
    }

    private void updateSELLFields(final UpdateExecutionCommandInput aCommand, final ExecutionBuilder executionBuilder) {
        executionBuilder
                .buyExecutedQuantity(aCommand.executedQuantity())
                .buyExecutedPrice(aCommand.executedPrice())
                .boughtAt(aCommand.executedAt());
    }
}
