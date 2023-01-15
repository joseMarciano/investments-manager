package com.investment.managment.execution.summarybystock;

import com.investment.managment.UseCase;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.wallet.WalletID;

import java.util.List;

public class ListExecutionUseCase extends UseCase<WalletID, List<ListExecutionCommandOutput>> {

    private final ExecutionGateway executionGateway;

    public ListExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public List<ListExecutionCommandOutput> execute(final WalletID aWalletID) {
        return this.executionGateway.getExecutionSummaryByStock(aWalletID)
                .stream().map(ListExecutionCommandOutput::from)
                .toList();
    }
}
