package com.investment.managment.execution.summarybystock;

import com.investment.managment.UseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.wallet.WalletID;

import java.util.List;

public class SummaryExecutionUseCase extends UseCase<WalletID, List<SummaryExecutionCommandOutput>> {

    private final ExecutionGateway executionGateway;

    public SummaryExecutionUseCase(final ExecutionGateway executionGateway) {
        this.executionGateway = executionGateway;
    }

    @Override
    public List<SummaryExecutionCommandOutput> execute(final WalletID aWalletID) {
        return this.executionGateway.getExecutionSummaryByStock(aWalletID)
                .stream().map(SummaryExecutionCommandOutput::from)
                .toList();
    }
}
