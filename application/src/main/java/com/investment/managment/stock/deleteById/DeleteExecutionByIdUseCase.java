package com.investment.managment.stock.deleteById;

import com.investment.managment.UnitUseCase;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.validation.exception.Error;

public class DeleteExecutionByIdUseCase extends UnitUseCase<ExecutionID> {

    private final ExecutionGateway executionGateway;

    public DeleteExecutionByIdUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public void execute(final ExecutionID anId) {
        if (this.executionGateway.findById(anId).isEmpty()) return;

        final boolean existsByOriginId =
                this.executionGateway.existsByOriginId(anId);

        if (existsByOriginId) {
            throw DomainException.of(new Error("There are others executions sold through this execution"));
        }

        this.executionGateway.deleteById(anId);
    }


}
