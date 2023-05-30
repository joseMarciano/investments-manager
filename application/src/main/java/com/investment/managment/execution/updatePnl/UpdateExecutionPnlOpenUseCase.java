package com.investment.managment.execution.updatePnl;

import com.investment.managment.UnitUseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;

import java.util.function.Consumer;

public class UpdateExecutionPnlOpenUseCase extends UnitUseCase<UpdateExecutionPnlOpenCommand> {

    private final ExecutionGateway executionGateway;

    public UpdateExecutionPnlOpenUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public void execute(final UpdateExecutionPnlOpenCommand input) {
        this.executionGateway.findById(input.id())
                .ifPresent(update(input));
    }

    private Consumer<Execution> update(final UpdateExecutionPnlOpenCommand input) {
        return execution -> {
            execution.update(
                    execution.getOrigin(),
                    execution.getStockId(),
                    execution.getWalletId(),
                    execution.getProfitPercentage(),
                    execution.getExecutedQuantity(),
                    execution.getExecutedPrice(),
                    execution.getStatus(),
                    input.pnlOpen(),
                    input.pnlOpenPercentage(),
                    execution.getPnlClose(),
                    execution.getPnlClosePercentage(),
                    execution.getExecutedAt()
            );

            this.executionGateway.update(execution);
        };
    }
}

