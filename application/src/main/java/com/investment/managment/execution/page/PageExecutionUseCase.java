package com.investment.managment.execution.page;

import com.investment.managment.UseCase;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.page.Pagination;

public class PageExecutionUseCase extends UseCase<ExecutionSearchQuery, Pagination<PageExecutionCommandOutput>> {

    private final ExecutionGateway executionGateway;

    public PageExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public Pagination<PageExecutionCommandOutput> execute(final ExecutionSearchQuery executionSearchQuery) {
        return executionGateway.findAll(executionSearchQuery)
                .map(PageExecutionCommandOutput::from);
    }
}
