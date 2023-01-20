package com.investment.managment.execution.gateway;

public interface ExecutionGateway extends
        CreateExecutionGateway,
        DeleteIdExecutionGateway,
        ExistsByOriginIdExecutionGateway,
        FindAllByOriginIdExecutionGateway,
        FindAllExecutionGateway,
        FindByIdExecutionGateway,
        GetExecutionSummaryByStockExecutionGateway,
        UpdateExecutionGateway {
}
