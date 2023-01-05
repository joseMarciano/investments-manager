package com.investment.managment.api.stock;

import com.investment.managment.DataBaseExtension;
import com.investment.managment.IntegrationTest;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.create.CreateStockUseCase;
import com.investment.managment.stock.persistence.StockJpaEntity;
import com.investment.managment.stock.persistence.StockRepository;
import com.investment.managment.stock.searcher.StockSearcher;
import com.investment.managment.stock.searcher.retrieve.StockRetrieveAllResponse;
import com.investment.managment.stock.update.UpdateStockUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@IntegrationTest
public class StockAPIScheduleTasksIT extends DataBaseExtension {

    @MockBean
    private StockSearcher stockSearcher;
    @SpyBean
    private UpdateStockUseCase updateStockUseCase;
    @SpyBean
    private CreateStockUseCase createStockUseCase;
    @Autowired
    private StockAPIScheduleTasks stockAPIScheduleTasks;
    @Autowired
    private StockRepository stockRepository;

    @Test
    public void givenAValidResponse_whenCallsUpdateOrCreateStocks_shouldCreateIt() {
        assertEquals(0, stockRepository.count());

        final var expectedSymbol = "PETR4";
        final var expectedName = "Petrobrás";

        when(stockSearcher.getAllTickers())
                .thenReturn(List.of(new StockRetrieveAllResponse(expectedName, expectedSymbol)));

        stockAPIScheduleTasks.updateOrCreateStocks();

        verify(this.createStockUseCase).execute(ArgumentMatchers.argThat(argument ->
                argument.symbol().equals(expectedSymbol)
        ));

        assertEquals(1, stockRepository.count());
    }

    @Test
    public void givenAValidResponse_whenCallsUpdateOrCreateStocksWithPreSavedStock_shouldUpdatedIt() {
        assertEquals(0, stockRepository.count());

        final var expectedSymbol = "PETR4";
        final var expectedName = "Petrobrás";
        final var expectedId = givenStock(
                StockBuilder.create().symbol(expectedSymbol).build()
        );
        assertEquals(1, stockRepository.count());


        when(stockSearcher.getAllTickers())
                .thenReturn(List.of(new StockRetrieveAllResponse(expectedName, expectedSymbol)));

        stockAPIScheduleTasks.updateOrCreateStocks();

        verify(this.updateStockUseCase).execute(ArgumentMatchers.argThat(argument ->
                argument.id().getValue().equals(expectedId) &&
                        argument.symbol().equals(expectedSymbol)
        ));

        assertEquals(1, stockRepository.count());
    }

    private String givenStock(final Stock aStock) {
        stockRepository.saveAndFlush(StockJpaEntity.from(aStock));

        return stockRepository.saveAndFlush(StockJpaEntity.from(aStock)).getId();
    }
}
