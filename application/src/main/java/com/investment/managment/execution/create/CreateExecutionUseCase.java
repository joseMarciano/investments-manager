package com.investment.managment.execution.create;

import com.investment.managment.UseCase;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;

import static com.investment.managment.validation.exception.DomainExeceptionFactory.notFoundException;
import static java.util.Objects.requireNonNull;

public class CreateExecutionUseCase extends UseCase<CreateExecutionCommandInput, CreateExecutionCommandOutput> {

    private final ExecutionGateway executionGateway;

    private final StockGateway stockGateway;

    private final WalletGateway walletGateway;

    public CreateExecutionUseCase(final ExecutionGateway executionGateway,
                                  final StockGateway stockGateway,
                                  final WalletGateway walletGateway) {
        this.executionGateway = requireNonNull(executionGateway);
        this.stockGateway = stockGateway;
        this.walletGateway = walletGateway;
    }

    @Override
    public CreateExecutionCommandOutput execute(final CreateExecutionCommandInput aCommand) {
        final var stockId = StockID.from(aCommand.stockId());
        final var walletId = WalletID.from(aCommand.walletId());

        this.stockGateway.findById(stockId)
                .orElseThrow(() -> notFoundException(stockId, Stock.class));
        this.walletGateway.findById(walletId)
                .orElseThrow(() -> notFoundException(walletId, Wallet.class));

        final var aExecution = ExecutionBuilder.create()
                .stockId(stockId)
                .walletId(walletId)
                .profitPercentage(aCommand.profitPercentage())
                .executedQuantity(aCommand.executedQuantity())
                .executedPrice(aCommand.executedPrice())
                .status(ExecutionStatus.BUY)
                .executedAt(aCommand.executedAt())
                .build();

        return CreateExecutionCommandOutput.from(executionGateway.create(aExecution));
    }
}
