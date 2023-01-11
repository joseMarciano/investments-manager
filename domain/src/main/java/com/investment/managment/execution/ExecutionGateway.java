package com.investment.managment.execution;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;

import java.util.Optional;

public interface ExecutionGateway {
    Execution create(Execution anExecution);

    Execution update(Execution anExecution);

    Optional<Execution> findById(ExecutionID anId);

    Pagination<Execution> findAll(SearchQuery query);

    void deleteById(ExecutionID id);
}
