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

import static java.util.Optional.ofNullable;

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
        final Long executedQuantity = 10L;
        final var executedPrice = BigDecimal.valueOf(4.85);
        final var executedVolume = BigDecimal.valueOf(48.5);
        final var executedAt = Instant.now();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, profitPercentage, executedQuantity, executedPrice, executedAt);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedQuantity", Matchers.is(executedQuantity.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedPrice", Matchers.is(executedPrice.doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedVolume", Matchers.is(executedVolume.doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(ExecutionStatus.BUY.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedAt", Matchers.is(executedAt.toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()));
    }

    @Test
    public void givenAInvalidStockId_whenCallsNewExecution_shouldReturn404() throws Exception {
        final var aWallet = persistWallet("Wallet");
        final var expectedStockId = StockID.from("invalid-id").getValue();
        final var expectedWalletId = aWallet.getId().getValue();
        final var profitPercentage = 8.00;
        final Long executedQuantity = 10L;
        final var executedPrice = BigDecimal.valueOf(4.85);
        final var executedAt = Instant.now();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, profitPercentage, executedQuantity, executedPrice, executedAt);

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
        final Long executedQuantity = 10L;
        final var executedPrice = BigDecimal.valueOf(4.85);
        final var executedAt = Instant.now();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, profitPercentage, executedQuantity, executedPrice, executedAt);

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
            "1, 25 , 7.52 ,  , 'executedAt' must not be null",
            "2.55,  , 7.52 , 2023-01-10T23:24:35.229493Z , 'executedQuantity' must not be null",
            "9, 25 , , 2023-01-10T23:24:35.229493Z , 'executedPrice' must not be null",
    })
    public void givenAInvalidEmptyName_whenCallsNewExecution_shouldReturn422(
            final Double expectedProfitPercentage,
            final Long expectedExecutedQuantity,
            final BigDecimal expectedExecutedPrice,
            final Instant expectedBoughtAt,
            final String expectedErrorMessage
    ) throws Exception {
        final var aStock = persistStock("PETR4F");
        final var aWallet = persistWallet("Wallet");
        final var expectedStockId = aStock.getId().getValue();
        final var expectedWalletId = aWallet.getId().getValue();

        final var actualRequestCommand = new CreateExecutionRequest(expectedStockId, expectedWalletId, expectedProfitPercentage, expectedExecutedQuantity, expectedExecutedPrice, expectedBoughtAt);

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
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now()
        );
        final var expectedWalletId = anExecution.getWalletId().getValue();
        final var expectedStockId = anExecution.getStockId().getValue();

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedQuantity", Matchers.is(expectedExecutedQuantity.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedPrice", Matchers.is(expectedExecutedPrice.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedVolume", Matchers.is(expectedExecutedVolume.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(anExecution.getStatus().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedAt", Matchers.is(expectedExecutedAt.toString())))
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
        Assertions.assertEquals(executionUpdated.getExecutedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(executionUpdated.getExecutedPrice(), expectedExecutedPrice.setScale(5));
        Assertions.assertEquals(executionUpdated.getExecutedVolume(), expectedExecutedVolume.setScale(5));
        Assertions.assertEquals(executionUpdated.getStatus(), anExecution.getStatus());
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
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now()
        );
        persistExecution(
                5L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                anExecution.getId().getValue(),
                InstantUtil.now()
        );
        final var expectedWalletId = anExecution.getWalletId().getValue();
        final var expectedStockId = anExecution.getStockId().getValue();

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedQuantity", Matchers.is(expectedExecutedQuantity.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedPrice", Matchers.is(expectedExecutedPrice.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedVolume", Matchers.is(expectedExecutedVolume.intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(anExecution.getStatus().name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.executedAt", Matchers.is(expectedExecutedAt.toString())))
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
        Assertions.assertEquals(executionUpdated.getExecutedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(executionUpdated.getExecutedPrice(), expectedExecutedPrice.setScale(5));
        Assertions.assertEquals(executionUpdated.getExecutedVolume(), expectedExecutedVolume.setScale(5));
        Assertions.assertEquals(executionUpdated.getStatus(), anExecution.getStatus());
        Assertions.assertEquals(executionUpdated.getExecutedAt(), expectedExecutedAt);
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
        final var expectedErrorMessage = "'executedPrice' can not be less than 10";

        final var anExecution = persistExecution(
                10L,
                BigDecimal.ONE,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now()
        );
        persistExecution(
                10L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                anExecution.getId().getValue(),
                InstantUtil.now()
        );

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
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
        final var expectedErrorMessage = "'executedPrice' should not be greater than 19";

        final var origin = persistExecution(
                120L,
                BigDecimal.ONE,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now()
        );
        final var anExecution = persistExecution(
                5L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                origin.getId().getValue(),
                InstantUtil.now()
        );

        persistExecution(
                101L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                origin.getId().getValue(),
                InstantUtil.now()
        );

        final var actualRequestCommand = new UpdateExecutionRequest(
                anExecution.getId().getValue(),
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

    @Test
    public void givenValidId_whenCallsDeleteByIdWithSoldExecutions_shouldReturn422() throws Exception {
        Assertions.assertEquals(0, this.executionRepository.count());
        final var expectedErrorMessage = "There are others executions sold through this execution";
        final var aExecution = persistExecution(
                120L,
                BigDecimal.ONE,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now()
        );
        persistExecution(
                101L,
                BigDecimal.TEN,
                ExecutionStatus.SELL,
                8.05,
                aExecution.getId().getValue(),
                InstantUtil.now()
        );
        Assertions.assertEquals(2, this.executionRepository.count());

        final RequestBuilder request =
                MockMvcRequestBuilders.delete(DEFAULT_PATH + "/{id}", aExecution.getId().getValue());

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)));
        Assertions.assertEquals(2, this.executionRepository.count());
    }

    @Test
    public void givenValidId_whenCallsDeleteById_shouldReturn204() throws Exception {
        Assertions.assertEquals(0, this.executionRepository.count());
        final var aExecution = persistExecution(
                120L,
                BigDecimal.ONE,
                ExecutionStatus.BUY,
                8.05,
                null,
                InstantUtil.now()
        );
        Assertions.assertEquals(1, this.executionRepository.count());

        final RequestBuilder request =
                MockMvcRequestBuilders.delete(DEFAULT_PATH + "/{id}", aExecution.getId().getValue());

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Assertions.assertEquals(0, this.executionRepository.count());
    }


    @Test
    public void givenInvalidId_whenCallsDeleteById_shouldReturn204() throws Exception {
        final var expectedId = ExecutionID.unique();

        final RequestBuilder request =
                MockMvcRequestBuilders.delete(DEFAULT_PATH + "/{id}", expectedId);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
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

    public Execution persistExecution(final Long executedQuantity,
                                      final BigDecimal executedPrice,
                                      final ExecutionStatus executionStatus,
                                      final Double profitPercentage,
                                      final String originID,
                                      final Instant executedAt
    ) {
        final var aStock = persistStock("PETR4F");
        final var aWallet = persistWallet("Wallet");
        return this.executionRepository.saveAndFlush(ExecutionJpaEntity.from(
                ExecutionBuilder.create()
                        .stockId(StockID.from(aStock.getId().getValue()))
                        .walletId(WalletID.from(aWallet.getId().getValue()))
                        .executedQuantity(executedQuantity)
                        .executedPrice(executedPrice)
                        .status(executionStatus)
                        .executedAt(executedAt)
                        .profitPercentage(profitPercentage)
                        .origin(ofNullable(originID).map(ExecutionID::from).orElse(null))
                        .build()
        )).toAggregate();
    }

}
