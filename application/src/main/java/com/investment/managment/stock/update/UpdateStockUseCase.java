package com.investment.managment.stock.update;

import com.investment.managment.UseCase;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.DomainExeceptionFactory;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.Wallet;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class UpdateStockUseCase extends UseCase<UpdateStockCommandInput, UpdateStockCommandOutput> {

    private final StockGateway stockGateway;

    public UpdateStockUseCase(final StockGateway stockGateway) {
        this.stockGateway = requireNonNull(stockGateway);
    }

    @Override
    public UpdateStockCommandOutput execute(final UpdateStockCommandInput aCommand) {
        final var anId = aCommand.id();
        final var aSymbol = aCommand.symbol();

        final var aStock = stockGateway.findById(anId)
                .orElseThrow(() -> DomainExeceptionFactory.notFoundException(anId, Stock.class));

        return UpdateStockCommandOutput.from(
                stockGateway.update(aStock.update(aSymbol))
        );
    }
}
