package com.investment.managment.execution.findById;

import com.investment.managment.UseCase;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.validation.exception.DomainExeceptionFactory;

public class FindExecutionByIdUseCase extends UseCase<ExecutionID, FindExecutionByIdCommandOutput> {

    private final ExecutionGateway executionGateway;

    public FindExecutionByIdUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public FindExecutionByIdCommandOutput execute(final ExecutionID anId) { // TODO: NEED TO BE TESTED
        return executionGateway.findById(anId)
                .map(FindExecutionByIdCommandOutput::from)
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(anId, Execution.class));
    }


}
