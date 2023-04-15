package com.investment.managment.api.stock;

import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.create.CreateStockCommandInput;
import com.investment.managment.stock.create.CreateStockUseCase;
import com.investment.managment.stock.searcher.StockSearcher;
import com.investment.managment.stock.searcher.retrieve.StockRetrieveAllResponse;
import com.investment.managment.stock.update.UpdateStockCommandInput;
import com.investment.managment.stock.update.UpdateStockUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class StockAPIScheduleTasksImpl implements StockAPIScheduleTasks {

    private final StockSearcher stockSearcher;
    private final StockGateway stockGateway;

    private final UpdateStockUseCase updateStockUseCase;
    private final CreateStockUseCase createStockUseCase;

    public StockAPIScheduleTasksImpl(final StockSearcher stockSearcher,
                                     final StockGateway stockGateway,
                                     final UpdateStockUseCase updateStockUseCase,
                                     final CreateStockUseCase createStockUseCase) {
        this.stockSearcher = stockSearcher;
        this.stockGateway = stockGateway;
        this.updateStockUseCase = updateStockUseCase;
        this.createStockUseCase = createStockUseCase;
    }

    @Override
    @Scheduled(cron = "@midnight", zone = "America/Sao_Paulo")
    public void updateOrCreateStocks() {
        this.stockSearcher.getAllTickers()
                .forEach(this::updateOrCreateStock);
    }

    private void updateOrCreateStock(final StockRetrieveAllResponse stockResponse) {
        final var symbol = stockResponse.ticker();

        this.stockGateway.findBySymbol(symbol)
                .ifPresentOrElse(update(stockResponse), save(stockResponse));
    }

    private Consumer<Stock> update(final StockRetrieveAllResponse stockResponse) {
        return stock -> {
            final var aCommand = UpdateStockCommandInput.with(
                    stock.getId().getValue(),
                    stockResponse.ticker()
            );

            this.updateStockUseCase.execute(aCommand);
        };
    }

    private Runnable save(final StockRetrieveAllResponse stockResponse) {
        return () -> {
            final var aCommand = CreateStockCommandInput.with(stockResponse.ticker());
            this.createStockUseCase.execute(aCommand);
        };
    }


}
