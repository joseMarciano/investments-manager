package com.investment.managment.execution.gateway;

import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import com.investment.managment.wallet.WalletID;

import java.util.List;

public interface GetExecutionSummaryByStockExecutionGateway {
    List<ExecutionSummaryByStock> getExecutionSummaryByStock(WalletID aWalletID);
}
