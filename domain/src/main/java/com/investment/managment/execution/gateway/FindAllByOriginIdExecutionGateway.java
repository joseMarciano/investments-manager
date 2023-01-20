package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.page.ExecutionSearchQuery;
import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import com.investment.managment.page.Pagination;
import com.investment.managment.wallet.WalletID;

import java.util.List;
import java.util.Optional;

public interface FindAllByOriginIdExecutionGateway {
    List<Execution> findAllByOriginId(ExecutionID originId);
}
