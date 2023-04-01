package com.investment.managment.execution.summarybystock;

import com.investment.managment.UseCase;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import com.investment.managment.execution.totalizator.ExecutionsTotalizatorCommandInput;
import com.investment.managment.execution.totalizator.ExecutionsTotalizatorUseCase;
import com.investment.managment.wallet.WalletID;

import java.util.List;
import java.util.function.Function;

public class SummaryExecutionUseCase extends UseCase<WalletID, List<SummaryExecutionCommandOutput>> {

    private final ExecutionGateway executionGateway;

    private final ExecutionsTotalizatorUseCase executionsTotalizatorUseCase;


    public SummaryExecutionUseCase(final ExecutionGateway executionGateway,
                                   final ExecutionsTotalizatorUseCase executionsTotalizatorUseCase) {
        this.executionGateway = executionGateway;
        this.executionsTotalizatorUseCase = executionsTotalizatorUseCase;
    }

    @Override
    public List<SummaryExecutionCommandOutput> execute(final WalletID aWalletID) {
        return this.executionGateway.getExecutionSummaryByStock(aWalletID)
                .stream()
                .map(buildOutput(aWalletID))
                .toList();
    }

    private Function<ExecutionSummaryByStock, SummaryExecutionCommandOutput> buildOutput(final WalletID aWalletID) {
        return executionSummaryByStock -> {
            final var totalizator =
                    this.executionsTotalizatorUseCase.execute(ExecutionsTotalizatorCommandInput.with(aWalletID.getValue(), executionSummaryByStock.stockId()));

            return SummaryExecutionCommandOutput.with(
                    executionSummaryByStock.stockId(),
                    executionSummaryByStock.symbol(),
                    executionSummaryByStock.totalQuantity(),
                    executionSummaryByStock.totalSoldQuantity(),
                    executionSummaryByStock.totalCustodyQuantity(),
                    totalizator.totalPnlClose(),
                    totalizator.totalPnlOpen()
            );
        };
    }

    ;
}
