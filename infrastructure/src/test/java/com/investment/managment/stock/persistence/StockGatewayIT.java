package com.investment.managment.stock.persistence;

import com.investment.managment.DataBaseExtension;
import com.investment.managment.IntegrationTest;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.investment.managment.stock.StockBuilder.create;


@IntegrationTest
public class StockGatewayIT extends DataBaseExtension {

    @Autowired
    private StockGateway stockGateway;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void injectTest() {
        Assertions.assertNotNull(stockGateway);
        Assertions.assertNotNull(stockRepository);
    }

    @Test
    public void givenAValidStock_whenCallsCreate_shouldPersistIt() {
        final var expectedSymbol = "PETR4";

        final Stock aStock = create()
                .symbol(expectedSymbol)
                .build();

        Assertions.assertEquals(0, stockRepository.count());
        final Stock actualStock = stockGateway.create(aStock);
        Assertions.assertEquals(1, stockRepository.count());

        Assertions.assertNotNull(aStock.getId());
        Assertions.assertEquals(aStock.getId(), actualStock.getId());
        Assertions.assertEquals(aStock.getSymbol(), actualStock.getSymbol());
        Assertions.assertNotNull(aStock.getCreatedAt());
        Assertions.assertEquals(aStock.getCreatedAt(), actualStock.getUpdatedAt());
        Assertions.assertEquals(aStock.getCreatedAt(), actualStock.getCreatedAt());
        Assertions.assertEquals(aStock.getUpdatedAt(), actualStock.getUpdatedAt());

        final StockJpaEntity stockJpaEntity = stockRepository.findById(aStock.getId().getValue()).get();

        Assertions.assertNotNull(stockJpaEntity.getId());
        Assertions.assertEquals(StockID.from(stockJpaEntity.getId()), actualStock.getId());
        Assertions.assertEquals(stockJpaEntity.getSymbol(), actualStock.getSymbol());
        Assertions.assertNotNull(stockJpaEntity.getCreatedAt());
        Assertions.assertEquals(stockJpaEntity.getCreatedAt().toString(), actualStock.getUpdatedAt().toString());
        Assertions.assertEquals(stockJpaEntity.getCreatedAt().toString(), actualStock.getCreatedAt().toString());
        Assertions.assertEquals(stockJpaEntity.getUpdatedAt().toString(), actualStock.getUpdatedAt().toString());
        Assertions.assertEquals(1, stockRepository.count());

    }

    @Test
    public void givenAValidStock_whenCallsUpdate_shouldUpdateIt() {
        final var expectedSymbol = "PETR4";
        var aStock = create()
                .symbol("pterr")
                .build();
        final var expectedId = aStock.getId();

        Assertions.assertEquals(0, stockRepository.count());
        var walletJPAEntity = stockRepository.saveAndFlush(StockJpaEntity.from(aStock));
        Assertions.assertEquals(1, stockRepository.count());

        Assertions.assertNotNull(aStock.getId());
        Assertions.assertEquals(aStock.getId().getValue(), walletJPAEntity.getId());
        Assertions.assertEquals(aStock.getSymbol(), walletJPAEntity.getSymbol());
        Assertions.assertNotNull(aStock.getCreatedAt());
        Assertions.assertEquals(aStock.getCreatedAt(), walletJPAEntity.getUpdatedAt());
        Assertions.assertEquals(aStock.getCreatedAt(), walletJPAEntity.getCreatedAt());
        Assertions.assertEquals(aStock.getUpdatedAt(), walletJPAEntity.getUpdatedAt());

        aStock.update(expectedSymbol);
        final var walletUpdated = stockGateway.update(aStock);

        Assertions.assertNotNull(walletUpdated.getId());
        Assertions.assertEquals(walletUpdated.getId(), expectedId);
        Assertions.assertEquals(walletUpdated.getSymbol(), expectedSymbol);
        Assertions.assertNotNull(walletUpdated.getCreatedAt());
        Assertions.assertNotNull(walletUpdated.getUpdatedAt());
        Assertions.assertTrue(walletUpdated.getCreatedAt().isBefore(walletUpdated.getUpdatedAt()));

        final var actualStock = stockRepository.findById(aStock.getId().getValue()).get().toAggregate();
        Assertions.assertNotNull(actualStock.getId());
        Assertions.assertEquals(actualStock.getId(), expectedId);
        Assertions.assertEquals(actualStock.getSymbol(), expectedSymbol);
        Assertions.assertNotNull(actualStock.getCreatedAt());
        Assertions.assertNotNull(actualStock.getUpdatedAt());
        Assertions.assertTrue(actualStock.getCreatedAt().isBefore(actualStock.getUpdatedAt()));
    }

    @Test
    public void givenAValidId_whenCallsFindById_shouldReturnIt() {
        final var expectedSymbol = "PETR4";
        var aStock = create()
                .symbol(expectedSymbol)
                .build();
        final var expectedId = aStock.getId();

        Assertions.assertEquals(0, stockRepository.count());
        stockRepository.saveAndFlush(StockJpaEntity.from(aStock));
        Assertions.assertEquals(1, stockRepository.count());

        final var actualStock = stockGateway.findById(expectedId).get();

        Assertions.assertNotNull(actualStock.getId());
        Assertions.assertEquals(actualStock.getId(), expectedId);
        Assertions.assertEquals(actualStock.getSymbol(), expectedSymbol);
        Assertions.assertNotNull(actualStock.getCreatedAt());
        Assertions.assertNotNull(actualStock.getUpdatedAt());
        Assertions.assertEquals(actualStock.getCreatedAt(), actualStock.getUpdatedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsFindById_shouldReturnEmpty() {
        final var anId = StockID.unique();

        Assertions.assertEquals(0, stockRepository.count());

        final var walletOptional = stockGateway.findById(anId);
        Assertions.assertTrue(walletOptional.isEmpty());
        Assertions.assertEquals(0, stockRepository.count());
    }

    @Test
    public void givenAValidId_whenCallsFindBySymbol_shouldReturnIt() {
        final var expectedSymbol = "PETR4";
        var aStock = create()
                .symbol(expectedSymbol)
                .build();
        final var expectedId = aStock.getId();

        Assertions.assertEquals(0, stockRepository.count());
        stockRepository.saveAndFlush(StockJpaEntity.from(aStock));
        Assertions.assertEquals(1, stockRepository.count());

        final var actualStock = stockGateway.findBySymbol(expectedSymbol).get();

        Assertions.assertNotNull(actualStock.getId());
        Assertions.assertEquals(actualStock.getId(), expectedId);
        Assertions.assertEquals(actualStock.getSymbol(), expectedSymbol);
        Assertions.assertNotNull(actualStock.getCreatedAt());
        Assertions.assertNotNull(actualStock.getUpdatedAt());
        Assertions.assertEquals(actualStock.getCreatedAt(), actualStock.getUpdatedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsFindBySymbol_shouldReturnEmpty() {
        Assertions.assertEquals(0, stockRepository.count());

        final var walletOptional = stockGateway.findBySymbol("ANY_SYMBOL");
        Assertions.assertTrue(walletOptional.isEmpty());
        Assertions.assertEquals(0, stockRepository.count());
    }

    @Test
    public void givenAValidId_whenCallsDeleteById_shouldDeleteIt() {
        final var expectedSymbol = "PETR4";
        var aStock = create()
                .symbol(expectedSymbol)
                .build();
        final var expectedId = aStock.getId();

        Assertions.assertEquals(0, stockRepository.count());
        stockRepository.saveAndFlush(StockJpaEntity.from(aStock));
        Assertions.assertEquals(1, stockRepository.count());

        stockGateway.deleteById(expectedId);

        Assertions.assertEquals(0, stockRepository.count());
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteById_shouldBeOk() {
        final var anId = StockID.unique();
        Assertions.assertEquals(0, stockRepository.count());
        Assertions.assertDoesNotThrow(() -> stockGateway.deleteById(anId));
        Assertions.assertEquals(0, stockRepository.count());
    }

    @ParameterizedTest
    @CsvSource({
            "0:1:symbol:asc, 2, HGLG11",
            "0:1:symbol:desc, 2, PETR4",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageCorrectly(final String params, final int expectedTotal, final String expectedSymbol) {
        final var queryParams = params.split(":");
        final var expectedOffset = Integer.valueOf(queryParams[0]);
        final var expectedLimit = Integer.valueOf(queryParams[1]);
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        persistStock(
                create().symbol("PETR4").build(),
                create().symbol("HGLG11").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Stock> actualPage = stockGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertEquals(actualPage.items().get(0).getSymbol(), expectedSymbol);
    }


    @ParameterizedTest
    @CsvSource({
            "0:1:symbol:asc, 4, A Day trade",
            "1:1:symbol:asc, 4, É muito bom",
            "2:1:symbol:asc, 4, God Day trade",
            "3:1:symbol:asc, 4, Zero lost"
    })
    public void givenAValidQuery_whenCallsFindAll_shouldPageCorrectly(final String params, final int expectedTotal, final String expectedSymbol) {
        final var queryParams = params.split(":");
        final var expectedOffset = Integer.valueOf(queryParams[0]);
        final var expectedLimit = Integer.valueOf(queryParams[1]);
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        persistStock(
                create().symbol("Zero lost").build(),
                create().symbol("A Day trade").build(),
                create().symbol("God Day trade").build(),
                create().symbol("É muito bom").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Stock> actualPage = stockGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertEquals(actualPage.items().get(0).getSymbol(), expectedSymbol);
    }

    @ParameterizedTest
    @CsvSource({
            "liker, Liker many things, 1",
            "ld, Should I s, 1",
            "@, Caracteres: +=12-!@, 1",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageFilteredCorrectly(final String expectedFilter, final String expectedSymbol, final int expectedTotal) {
        final var expectedOffset = 0;
        final var expectedLimit = 20;
        final var expectedSort = "symbol";
        final var expectedDirection = "desc";

        persistStock(
                create().symbol("Liker many things").build(),
                create().symbol("Should I s").build(),
                create().symbol("Caracteres: +=12-!@").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Stock> actualPage = stockGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertEquals(actualPage.items().get(0).getSymbol(), expectedSymbol);
    }

    @ParameterizedTest
    @CsvSource({
            "consumer",
            "(mui bien)",
            "there are",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnEmptyPageCorrectly(final String expectedFilter) {
        final var expectedOffset = 0;
        final var expectedLimit = 20;
        final var expectedSort = "symbol";
        final var expectedDirection = "desc";
        final var expectedTotal = 0;

        persistStock(
                create().symbol("Liker many things").build(),
                create().symbol("Should I stay").build(),
                create().symbol("Caracteres: +=12-!@").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Stock> actualPage = stockGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertTrue(actualPage.items().isEmpty());
    }

    private void persistStock(final Stock... wallets) {
        for (Stock wallet : wallets) {
            stockRepository.saveAndFlush(StockJpaEntity.from(wallet));
        }
    }


}
