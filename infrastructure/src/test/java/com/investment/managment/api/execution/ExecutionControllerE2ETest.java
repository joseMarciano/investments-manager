package com.investment.managment.api.execution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.managment.DataBaseExtension;
import com.investment.managment.E2ETest;
import com.investment.managment.api.execution.models.CreateExecutionRequest;
import com.investment.managment.api.execution.models.UpdateExecutionRequest;
import com.investment.managment.api.execution.models.UpdateExecutionResponse;
import com.investment.managment.config.json.Json;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.persistence.ExecutionJpaEntity;
import com.investment.managment.execution.persistence.ExecutionRepository;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.StockID;
import com.investment.managment.stock.persistence.StockJpaEntity;
import com.investment.managment.stock.persistence.StockRepository;
import com.investment.managment.util.InstantUtil;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletID;
import com.investment.managment.wallet.persistence.WalletJpaEntity;
import com.investment.managment.wallet.persistence.WalletRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;

@E2ETest
public class ExecutionControllerE2ETest extends DataBaseExtension {

    private static final String DEFAULT_PATH = "/executions";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ExecutionRepository executionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenAValidParams_whenCallsNewExecution_shouldInstantiateIt() throws Exception {
        final var aStock = persistStock("PETR4F");
        final var aWallet = persistWallet("Wallet");
        final var expectedStockId = aStock.getId().getValue();
        final var expectedWalletId = aWallet.getId().getValue();
        final var profitPercentage = 8.00;
        final Long buyExecutedQuantity = 10L;
        final var buyExecutedPrice = BigDecimal.valueOf(4.85);
        final var buyExecutedVolume = BigDecimal.valueOf(48.5);
        final var boughtAt = Instant.now();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, profitPercentage, buyExecutedQuantity, buyExecutedPrice, boughtAt);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockId", Matchers.is(expectedStockId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletId", Matchers.is(expectedWalletId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profitPercentage", Matchers.is(profitPercentage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedQuantity", Matchers.is(buyExecutedQuantity.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedPrice", Matchers.is(buyExecutedPrice.doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedVolume", Matchers.is(buyExecutedVolume.doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(ExecutionStatus.BUY.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boughtAt", Matchers.is(boughtAt.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()));
    }

    @Test
    public void givenAInvalidStockId_whenCallsNewExecution_shouldReturn404() throws Exception {
        final var aWallet = persistWallet("Wallet");
        final var expectedStockId = StockID.from("invalid-id").getValue();
        final var expectedWalletId = aWallet.getId().getValue();
        final var profitPercentage = 8.00;
        final Long buyExecutedQuantity = 10L;
        final var buyExecutedPrice = BigDecimal.valueOf(4.85);
        final var boughtAt = Instant.now();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, profitPercentage, buyExecutedQuantity, buyExecutedPrice, boughtAt);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAInvalidWalletId_whenCallsNewExecution_shouldReturn404() throws Exception {
        final var aStock = persistStock("PETR4F");
        final var expectedStockId = aStock.getId().getValue();
        final var expectedWalletId = WalletID.from("invalid-id").getValue();
        final var profitPercentage = 8.00;
        final Long buyExecutedQuantity = 10L;
        final var buyExecutedPrice = BigDecimal.valueOf(4.85);
        final var boughtAt = Instant.now();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, profitPercentage, buyExecutedQuantity, buyExecutedPrice, boughtAt);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Should return exception to invalid params")
    @ParameterizedTest
    @CsvSource(value = {
            ", 25 , 7.52 , 2023-01-10T23:24:35.229493Z , 'profitPercentage' must not be null",
            "0.0, 25 , 7.52 , 2023-01-10T23:24:35.229493Z , 'profitPercentage' should be bigger than 0.0",
            "1, 25 , 7.52 ,  , 'boughtAt' must not be null",
            "2.55,  , 7.52 , 2023-01-10T23:24:35.229493Z , 'buyExecutedQuantity' must not be null",
            "9, 25 , , 2023-01-10T23:24:35.229493Z , 'buyExecutedPrice' must not be null",
    })
    public void givenAInvalidEmptyName_whenCallsNewExecution_shouldReturn422(
            final Double expectedProfitPercentage,
            final Long expectedBuyExecutedQuantity,
            final BigDecimal expectedBuyExecutedPrice,
            final Instant expectedBoughtAt,
            final String expectedErrorMessage
    ) throws Exception {
        final var aStock = persistStock("PETR4F");
        final var aWallet = persistWallet("Wallet");
        final var expectedStockId = aStock.getId().getValue();
        final var expectedWalletId = aWallet.getId().getValue();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, expectedProfitPercentage, expectedBuyExecutedQuantity, expectedBuyExecutedPrice, expectedBoughtAt);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void givenAValidExecution_whenCallsUpdateWithoutExecutionsSold_shouldUpdateIt() throws Exception {
        final var expectedProfitPercentage = 9.7;
        final Long expectedExecutedQuantity = 9L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedExecutedVolume = BigDecimal.valueOf(90);
        final var anExecution = persistExecution(
                10L,
                BigDecimal.ONE,
                null,
                null,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now(),
                null
        );
        final var expectedWalletId = anExecution.getWalletId().getValue();
        final var expectedStockId = anExecution.getStockId().getValue();

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
                anExecution.getStockId().getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", anExecution.getId().getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockId", Matchers.is(expectedStockId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletId", Matchers.is(expectedWalletId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profitPercentage", Matchers.is(expectedProfitPercentage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedQuantity", Matchers.is(expectedExecutedQuantity.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedPrice", Matchers.is(expectedExecutedPrice.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedVolume", Matchers.is(expectedExecutedVolume.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellExecutedQuantity", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(anExecution.getStatus().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellExecutedPrice", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellExecutedVolume", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boughtAt", Matchers.is(expectedExecutedAt.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.soldAt", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()))
                .andExpect(result -> {
                    final UpdateExecutionResponse updateExecutionResponse =
                            Json.readValue(result.getResponse().getContentAsString(), UpdateExecutionResponse.class);
                    Assertions.assertTrue(updateExecutionResponse.createdAt().isBefore(updateExecutionResponse.updatedAt()));
                });


        final ExecutionJpaEntity executionUpdated =
                this.executionRepository.findById(anExecution.getId().getValue()).get();


        Assertions.assertEquals(executionUpdated.getId(), anExecution.getId().getValue());
        Assertions.assertNull(executionUpdated.getOrigin());
        Assertions.assertEquals(executionUpdated.getStock().getId(), anExecution.getStockId().getValue());
        Assertions.assertEquals(executionUpdated.getWallet().getId(), anExecution.getWalletId().getValue());
        Assertions.assertEquals(executionUpdated.getProfitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(executionUpdated.getBuyExecutedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(executionUpdated.getBuyExecutedPrice(), expectedExecutedPrice.setScale(5));
        Assertions.assertEquals(executionUpdated.getBuyExecutedVolume(), expectedExecutedVolume.setScale(5));
        Assertions.assertNull(executionUpdated.getSellExecutedQuantity());
        Assertions.assertNull(executionUpdated.getSellExecutedPrice());
        Assertions.assertNull(executionUpdated.getSellExecutedVolume());
        Assertions.assertEquals(executionUpdated.getStatus(), anExecution.getStatus());
        Assertions.assertEquals(executionUpdated.getBoughtAt(), expectedExecutedAt);
        Assertions.assertNull(executionUpdated.getSoldAt());
        Assertions.assertEquals(executionUpdated.getCreatedAt(), anExecution.getCreatedAt());
        Assertions.assertTrue(executionUpdated.getCreatedAt().isBefore(executionUpdated.getUpdatedAt()));
    }

    @Test
    public void givenAValidExecution_whenCallsUpdateWithExecutionsSold_shouldUpdateIt() throws Exception {
        final var expectedProfitPercentage = 9.7;
        final Long expectedExecutedQuantity = 9L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedExecutedVolume = BigDecimal.valueOf(90);
        final var anExecution = persistExecution(
                10L,
                BigDecimal.ONE,
                null,
                null,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now(),
                null
        );
        persistExecution(
                null,
                null,
                8L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                anExecution.getId().getValue(),
                null,
                InstantUtil.now()
        );
        final var expectedWalletId = anExecution.getWalletId().getValue();
        final var expectedStockId = anExecution.getStockId().getValue();

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
                anExecution.getStockId().getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", anExecution.getId().getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stockId", Matchers.is(expectedStockId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletId", Matchers.is(expectedWalletId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.profitPercentage", Matchers.is(expectedProfitPercentage)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedQuantity", Matchers.is(expectedExecutedQuantity.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedPrice", Matchers.is(expectedExecutedPrice.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.buyExecutedVolume", Matchers.is(expectedExecutedVolume.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellExecutedQuantity", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(anExecution.getStatus().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellExecutedPrice", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sellExecutedVolume", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.boughtAt", Matchers.is(expectedExecutedAt.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.soldAt", Matchers.nullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()))
                .andExpect(result -> {
                    final UpdateExecutionResponse updateExecutionResponse =
                            Json.readValue(result.getResponse().getContentAsString(), UpdateExecutionResponse.class);
                    Assertions.assertTrue(updateExecutionResponse.createdAt().isBefore(updateExecutionResponse.updatedAt()));
                });


        final ExecutionJpaEntity executionUpdated =
                this.executionRepository.findById(anExecution.getId().getValue()).get();


        Assertions.assertEquals(executionUpdated.getId(), anExecution.getId().getValue());
        Assertions.assertNull(executionUpdated.getOrigin());
        Assertions.assertEquals(executionUpdated.getStock().getId(), anExecution.getStockId().getValue());
        Assertions.assertEquals(executionUpdated.getWallet().getId(), anExecution.getWalletId().getValue());
        Assertions.assertEquals(executionUpdated.getProfitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(executionUpdated.getBuyExecutedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(executionUpdated.getBuyExecutedPrice(), expectedExecutedPrice.setScale(5));
        Assertions.assertEquals(executionUpdated.getBuyExecutedVolume(), expectedExecutedVolume.setScale(5));
        Assertions.assertNull(executionUpdated.getSellExecutedQuantity());
        Assertions.assertNull(executionUpdated.getSellExecutedPrice());
        Assertions.assertNull(executionUpdated.getSellExecutedVolume());
        Assertions.assertEquals(executionUpdated.getStatus(), anExecution.getStatus());
        Assertions.assertEquals(executionUpdated.getBoughtAt(), expectedExecutedAt);
        Assertions.assertNull(executionUpdated.getSoldAt());
        Assertions.assertEquals(executionUpdated.getCreatedAt(), anExecution.getCreatedAt());
        Assertions.assertTrue(executionUpdated.getCreatedAt().isBefore(executionUpdated.getUpdatedAt()));
    }

    @DisplayName("Should return Domain Exception when the new executed quantity is less sum of all sold executed quantity")
    @Test
    public void givenAShortExecutedQuantity_whenCallsUpdateExecutionWithSoldExecutions_shouldReturn422() throws Exception {
        final var expectedProfitPercentage = 9.7;
        final Long expectedExecutedQuantity = 5L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedErrorMessage = "'buyExecutedPrice' can not be less than 10";

        final var anExecution = persistExecution(
                10L,
                BigDecimal.ONE,
                null,
                null,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now(),
                null
        );
        persistExecution(
                null,
                null,
                10L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                anExecution.getId().getValue(),
                null,
                InstantUtil.now()
        );

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
                anExecution.getStockId().getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        final RequestBuilder request =
                MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", anExecution.getId().getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Json.writeValueAsString(actualRequestCommand));


        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("Should return Domain Exception when the sold execution is edited and the sold executed quantity is bigger than remaining quantity")
    @Test
    public void givenABiggerExecutedQuantity_whenCallsUpdateExecutionWithSellStatus_shouldReturn422() throws Exception {
        final var expectedProfitPercentage = 9.7;
        final Long expectedExecutedQuantity = 20L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedErrorMessage = "'sellExecutedPrice' should not be greater than 19";

        final var origin = persistExecution(
                120L,
                BigDecimal.ONE,
                null,
                null,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now(),
                null
        );
        final var anExecution = persistExecution(
                null,
                null,
                5L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                origin.getId().getValue(),
                null,
                InstantUtil.now()
        );

        persistExecution(
                null,
                null,
                101L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                origin.getId().getValue(),
                null,
                InstantUtil.now()
        );

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
                anExecution.getStockId().getValue(),
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        final RequestBuilder request =
                MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", anExecution.getId().getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Json.writeValueAsString(actualRequestCommand));


        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenInvalidId_whenCallsUpdate_shouldReturnNotFound() throws Exception {
        final var expectedProfitPercentage = 9.7;
        final Long expectedExecutedQuantity = 20L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedId = ExecutionID.from("invalid").getValue();


        final var actualRequestCommand = new UpdateExecutionRequest(
                expectedId,
                null,
                expectedProfitPercentage,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        final RequestBuilder request =
                MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", expectedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Json.writeValueAsString(actualRequestCommand));


        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    public Stock persistStock(final String symbol) {
        return this.stockRepository
                .saveAndFlush(StockJpaEntity.from(StockBuilder.create().symbol(symbol).build())).toAggregate();
    }

    public Wallet persistWallet(final String name) {
        return this.walletRepository
                .saveAndFlush(WalletJpaEntity.from(WalletBuilder.create().name(name).build()))
                .toAggregate();
    }

    public Execution persistExecution(final Long buyExecutedQuantity,
                                      final BigDecimal buyExecutedPrice,
                                      final Long sellExecutedQuantity,
                                      final BigDecimal sellExecutedPrice,
                                      final ExecutionStatus executionStatus,
                                      final Double profitPercentage,
                                      final String originID,
                                      final Instant boughtAt,
                                      final Instant soldAt
    ) {
        final var aStock = persistStock("PETR4F");
        final var aWallet = persistWallet("Wallet");
        return this.executionRepository.saveAndFlush(ExecutionJpaEntity.from(
                ExecutionBuilder.create()
                        .stockId(StockID.from(aStock.getId().getValue()))
                        .walletId(WalletID.from(aWallet.getId().getValue()))
                        .buyExecutedQuantity(buyExecutedQuantity)
                        .sellExecutedQuantity(sellExecutedQuantity)
                        .buyExecutedPrice(buyExecutedPrice)
                        .sellExecutedPrice(sellExecutedPrice)
                        .status(executionStatus)
                        .boughtAt(boughtAt)
                        .soldAt(soldAt)
                        .profitPercentage(profitPercentage)
                        .origin(ExecutionID.from(originID))
                        .build()
        )).toAggregate();
    }

}