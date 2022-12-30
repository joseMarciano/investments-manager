package com.investment.managment.stock.page;

import com.investment.managment.UseCaseTest;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@UseCaseTest
public class PageStockUseCaseTest {

    @InjectMocks
    private PageStockUseCase pageStockUseCase;

    @Mock
    private StockGateway stockGateway;

    @Test
    public void givenAValidParams_whenCallsUseCase_shouldReturnFilledPage() {
        final var expectedOffset = 0;
        final var expectedLimit = 20;
        final var expectedSort = "symbol";
        final var expectedDirection = "asc";
        final var expectedTerm = "PE";
        final var expectedTotal = 1;
        final var aSearchQuery = new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedTerm);

        final var aStock = StockBuilder.create()
                .symbol("PETR4")
                .build();

        when(stockGateway.findAll(eq(aSearchQuery)))
                .thenReturn(new Pagination<>(expectedOffset, expectedLimit, expectedTotal, List.of(aStock)));

        final var actualOutput = pageStockUseCase.execute(aSearchQuery);

        Assertions.assertEquals(actualOutput.limit(), expectedLimit);
        Assertions.assertEquals(actualOutput.offset(), expectedOffset);
        Assertions.assertEquals(actualOutput.total(), expectedTotal);
        Assertions.assertEquals(actualOutput.items().get(0).id(), aStock.getId());
        Assertions.assertEquals(actualOutput.items().get(0).symbol(), aStock.getSymbol());
    }

    @Test
    public void givenAValidParams_whenCallsUseCase_shouldReturnEmptyPage() {
        final var expectedOffset = 10;
        final var expectedLimit = 20;
        final var expectedSort = "symbol";
        final var expectedDirection = "asc";
        final var expectedTerm = "PE";
        final var expectedTotal = 0;
        final var aSearchQuery = new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedTerm);

        when(stockGateway.findAll(eq(aSearchQuery)))
                .thenReturn(new Pagination<>(expectedOffset, expectedLimit, expectedTotal, Collections.emptyList()));

        final var actualOutput = pageStockUseCase.execute(aSearchQuery);

        Assertions.assertEquals(actualOutput.limit(), expectedLimit);
        Assertions.assertEquals(actualOutput.offset(), expectedOffset);
        Assertions.assertEquals(actualOutput.total(), expectedTotal);
        Assertions.assertTrue(actualOutput.items().isEmpty());
    }
}
