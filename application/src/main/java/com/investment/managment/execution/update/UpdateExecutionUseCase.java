package com.investment.managment.execution.update;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.update.buy.UpdateBuyFieldsExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellFieldsExecutionUseCase;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;

import static com.investment.managment.execution.ExecutionStatus.SELL;
import static com.investment.managment.validation.exception.DomainExeceptionFactory.notFoundException;
import static java.util.Objects.requireNonNull;

public class UpdateExecutionUseCase extends UseCase<UpdateExecutionCommandInput, UpdateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    private final StockGateway stockGateway;

    private final UpdateBuyFieldsExecutionUseCase updateBuyFieldsExecutionUseCase;

    private final UpdateSellFieldsExecutionUseCase updateSellFieldsExecutionUseCase;


    public UpdateExecutionUseCase(final ExecutionGateway executionGateway,
                                  final StockGateway stockGateway,
                                  final UpdateBuyFieldsExecutionUseCase updateBuyFieldsExecutionUseCase,
                                  final UpdateSellFieldsExecutionUseCase updateSellFieldsExecutionUseCase) {
        this.executionGateway = requireNonNull(executionGateway);
        this.stockGateway = requireNonNull(stockGateway);
        this.updateBuyFieldsExecutionUseCase = updateBuyFieldsExecutionUseCase;
        this.updateSellFieldsExecutionUseCase = updateSellFieldsExecutionUseCase;
    }

    @Override
    public UpdateExecutionCommandOutput execute(final UpdateExecutionCommandInput aCommand) {
        final var stockId = StockID.from(aCommand.stockId());
        final var executionID = ExecutionID.from(aCommand.id());

        this.stockGateway.findById(stockId)
                .orElseThrow(() -> notFoundException(stockId, Stock.class));

        final var anExecution = this.executionGateway.findById(executionID)
                .orElseThrow(() -> notFoundException(executionID, Execution.class));

        if (SELL.equals(anExecution.getStatus())) return this.updateSellFieldsExecutionUseCase.execute(aCommand);

        return this.updateBuyFieldsExecutionUseCase.execute(aCommand);
    }


    private void resolveToBuyStatus(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        final ExecutionID executionID = anExecution.getId();

        if (!this.executionGateway.existsByOriginId(executionID)) {
            updateBUYFields(aCommand, anExecution);
            return;
        }


        this.executionGateway.findAllByOriginId(executionID);


    }

    private void updateBUYFields(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        anExecution
                .update(
                        anExecution.getOrigin(),
                        StockID.from(aCommand.stockId()),
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
    }

    private void updateSELLFields(final UpdateExecutionCommandInput aCommand, final Execution anExecution) {
        anExecution
                .update(
                        anExecution.getOrigin(),
                        StockID.from(aCommand.stockId()),
                        anExecution.getWalletId(),
                        aCommand.profitPercentage(),
                        anExecution.getBuyExecutedQuantity(),
                        anExecution.getBuyExecutedPrice(),
                        aCommand.executedQuantity(),
                        aCommand.executedPrice(),
                        anExecution.getStatus(),
                        anExecution.getBoughtAt(),
                        aCommand.executedAt()
                );
    }
}
