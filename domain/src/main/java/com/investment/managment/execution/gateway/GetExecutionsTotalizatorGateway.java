package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.util.List;

public interface GetExecutionsTotalizatorGateway {
    List<Execution> getExecutionsByStockIdAndWalletId(WalletID aWalletID, StockID aStockID);
}
