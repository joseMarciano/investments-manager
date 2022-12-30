package com.investment.managment.stock.create;

import com.investment.managment.UseCase;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.StockGateway;

import static java.util.Objects.requireNonNull;

public class CreateStockUseCase extends UseCase<CreateStockCommandInput, CreateStockCommandOutput> {

    private final StockGateway stockGateway;

    public CreateStockUseCase(final StockGateway stockGateway) {
        this.stockGateway = requireNonNull(stockGateway);
    }

    @Override
    public CreateStockCommandOutput execute(final CreateStockCommandInput aCommand) {
        final var aStock = StockBuilder.create()
                .symbol(aCommand.symbol())
                .build();

        return CreateStockCommandOutput.from(stockGateway.create(aStock));
    }
}
