package com.investment.managment.execution;

import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class ExecutionTest {

    @Test
    public void givenAValidParams_whenCallsNewExecution_shouldInstantiateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedVolume = BigDecimal.valueOf(1000L);
        final var expectedExecutedAt = Instant.now();

        final var actualExecution = ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .status(expectedStatus)
                .executedAt(expectedExecutedAt)
                .profitPercentage(expectedProfitPercentage)
                .origin(expectedOrigin)
                .build();


        Assertions.assertNotNull(actualExecution.getId());
        Assertions.assertEquals(actualExecution.getStockId(), expectedStockId);
        Assertions.assertEquals(actualExecution.getWalletId(), expectedWalletId);
        Assertions.assertEquals(actualExecution.getExecutedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(actualExecution.getExecutedPrice(), expectedExecutedPrice);
        Assertions.assertEquals(actualExecution.getExecutedVolume(), expectedExecutedVolume);
        Assertions.assertEquals(actualExecution.getProfitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualExecution.getExecutedAt(), expectedExecutedAt);
        Assertions.assertEquals(actualExecution.getStatus(), expectedStatus);
        Assertions.assertEquals(actualExecution.getOrigin(), expectedOrigin);
        Assertions.assertNotNull(actualExecution.getCreatedAt());
        Assertions.assertNotNull(actualExecution.getUpdatedAt());
        Assertions.assertEquals(actualExecution.getUpdatedAt(), actualExecution.getCreatedAt());
    }

    @Test
    public void givenAParamWithOriginIdAndStatusBuy_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 4.07;
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedOrigin = ExecutionID.unique();
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'originId' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .executedAt(expectedExecutedAt)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidOriginIdWithStatusSell_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 4.07;
        final var expectedStatus = ExecutionStatus.SELL;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'originId' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .executedAt(expectedExecutedAt)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullProfitPercentage_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final Double expectedProfitPercentage = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'profitPercentage' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .executedAt(expectedExecutedAt)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidZeroProfitPercentage_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 0.000;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'profitPercentage' should be bigger than 0.0";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidLessThanZeroExecutedQuantity_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = -5L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 1.0;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'executedQuantity' should be bigger than 0.0";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidLessThanZeroExecutedPrice_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 5L;
        final var expectedExecutedPrice = BigDecimal.valueOf(-8);
        final var expectedProfitPercentage = 1.0;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'executedPrice' should be bigger than 0.0";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidLessThanZeroProfitPercentage_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = -0.1;
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedExecutedAt = Instant.now();

        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'profitPercentage' should be bigger than 0.0";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullStockId_whenCallsNewExecution_shouldReturnADomainException() {
        final StockID expectedStockId = null;
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'stockId' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullExecutedAt_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.BUY;
        final var profitPercentage = 4.05;
        final ExecutionID expectedOrigin = null;
        final Instant expectedExecutedAt = null;

        final var expectedErrorMessage = "'executedAt' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .profitPercentage(profitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullWalletId_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final WalletID expectedWalletId = null;
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'walletId' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullStatus_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedExecutedQuantity = 100L;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final ExecutionStatus expectedStatus = null;
        final ExecutionID expectedOrigin = null;
        final var expectedExecutedAt = Instant.now();
        final var expectedErrorMessage = "'status' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .executedAt(expectedExecutedAt)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithInvalidNullBuyExecutedQuantity_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedExecutedQuantity = null;
        final Long expectedSellExecutedQuantity = null;
        final var expectedExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'executedQuantity' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithInvalidNullBuyExecutedPrice_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final BigDecimal expectedExecutedPrice = null;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'executedPrice' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .executedQuantity(expectedExecutedQuantity)
                .executedPrice(expectedExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }
}
