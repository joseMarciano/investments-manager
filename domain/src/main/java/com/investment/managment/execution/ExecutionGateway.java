package com.investment.managment.execution;

import com.investment.managment.execution.page.ExecutionSearchQuery;
import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import com.investment.managment.page.Pagination;
import com.investment.managment.wallet.WalletID;

import java.util.List;
import java.util.Optional;

public interface ExecutionGateway {
    Execution create(Execution anExecution);

    Execution update(Execution anExecution);

    Optional<Execution> findById(ExecutionID anId);

    Pagination<Execution> findAll(ExecutionSearchQuery executionSearchQuery);

    List<Execution> findAllByOriginId(ExecutionID originId);

    boolean existsByOriginId(ExecutionID... originId);

    void deleteById(ExecutionID id);

    List<ExecutionSummaryByStock> getExecutionSummaryByStock(WalletID aWalletID);
}
