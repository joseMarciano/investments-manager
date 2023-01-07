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
    public void givenAValidParams_whenCallsNewExecutionWithBUYStatus_shouldInstantiateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOrigin = null;
        final var expectedBuyExecutedVolume = BigDecimal.valueOf(1000L);
        final var expectedBoughtAt = Instant.now();

        final var actualWallet = ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .boughtAt(expectedBoughtAt)
                .profitPercentage(expectedProfitPercentage)
                .origin(expectedOrigin)
                .build();


        Assertions.assertNotNull(actualWallet.getId());
        Assertions.assertEquals(actualWallet.getStockId(), expectedStockId);
        Assertions.assertEquals(actualWallet.getWalletId(), expectedWalletId);
        Assertions.assertEquals(actualWallet.getBuyExecutedQuantity(), expectedBuyExecutedQuantity);
        Assertions.assertEquals(actualWallet.getSellExecutedQuantity(), expectedSellExecutedQuantity);
        Assertions.assertEquals(actualWallet.getBuyExecutedPrice(), expectedBuyExecutedPrice);
        Assertions.assertEquals(actualWallet.getSellExecutedPrice(), expectedSellExecutedPrice);
        Assertions.assertEquals(actualWallet.getBuyExecutedVolume(), expectedBuyExecutedVolume);
        Assertions.assertEquals(actualWallet.getProfitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualWallet.getBoughtAt(), expectedBoughtAt);
        Assertions.assertNull(actualWallet.getSoldAt());
        Assertions.assertNull(actualWallet.getSellExecutedVolume());
        Assertions.assertEquals(actualWallet.getStatus(), expectedStatus);
        Assertions.assertEquals(actualWallet.getOrigin(), expectedOrigin);
        Assertions.assertNotNull(actualWallet.getCreatedAt());
        Assertions.assertNotNull(actualWallet.getUpdatedAt());
        Assertions.assertEquals(actualWallet.getUpdatedAt(), actualWallet.getCreatedAt());
    }

    @Test
    public void givenAValidParams_whenCallsNewExecutionWithSELLStatus_shouldInstantiateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final var expectedSellExecutedQuantity = 11L;
        final BigDecimal expectedBuyExecutedPrice = null;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 5.00;
        final var expectedStatus = ExecutionStatus.SELL;
        final ExecutionID expectedOrigin = null;
        final var expectedBuyExecutedVolume = BigDecimal.valueOf(110);
        final var expectedSoldAt = Instant.now();

        final var actualWallet = ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .soldAt(expectedSoldAt)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build();


        Assertions.assertNotNull(actualWallet.getId());
        Assertions.assertEquals(actualWallet.getStockId(), expectedStockId);
        Assertions.assertEquals(actualWallet.getWalletId(), expectedWalletId);
        Assertions.assertEquals(actualWallet.getBuyExecutedQuantity(), expectedBuyExecutedQuantity);
        Assertions.assertEquals(actualWallet.getSellExecutedQuantity(), expectedSellExecutedQuantity);
        Assertions.assertEquals(actualWallet.getBuyExecutedPrice(), expectedBuyExecutedPrice);
        Assertions.assertEquals(actualWallet.getSellExecutedPrice(), expectedSellExecutedPrice);
        Assertions.assertEquals(actualWallet.getSellExecutedVolume(), expectedBuyExecutedVolume);
        Assertions.assertEquals(actualWallet.getProfitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualWallet.getSoldAt(), expectedSoldAt);
        Assertions.assertNull(actualWallet.getBoughtAt());
        Assertions.assertNull(actualWallet.getBuyExecutedVolume());
        Assertions.assertEquals(actualWallet.getStatus(), expectedStatus);
        Assertions.assertEquals(actualWallet.getOrigin(), expectedOrigin);
        Assertions.assertNotNull(actualWallet.getCreatedAt());
        Assertions.assertNotNull(actualWallet.getUpdatedAt());
        Assertions.assertEquals(actualWallet.getUpdatedAt(), actualWallet.getCreatedAt());
    }

    @Test
    public void givenAInvalidNullProfitPercentage_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final Double expectedProfitPercentage = null;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'profitPercentage' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidZeroProfitPercentage_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 0.000;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'profitPercentage' should be bigger than 0.0";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidLessThanZeroProfitPercentage_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = -0.1;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'profitPercentage' should be bigger than 0.0";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullStockId_whenCallsNewExecution_shouldReturnADomainException() {
        final StockID expectedStockId = null;
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'stockId' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithBoughtAtFilledAndStatusSELL_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final var expectedSellExecutedQuantity = 1L;
        final BigDecimal expectedBuyExecutedPrice = null;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOrigin = null;
        final var expectedBoughtAt = Instant.now();
        final Instant expectedSoldAt = null;

        final var expectedErrorMessage = "'boughtAt' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .boughtAt(expectedBoughtAt)
                .soldAt(expectedSoldAt)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithSoldAtFilledAndStatusBUY_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 1L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.ONE;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOrigin = null;
        final var expectedBoughtAt = Instant.now();
        final var expectedSoldAt = Instant.now();

        final var expectedErrorMessage = "'soldAt' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .boughtAt(expectedBoughtAt)
                .soldAt(expectedSoldAt)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }
    @Test
    public void givenParamsWithNullBoughtAtFilled_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 1L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.ONE;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOrigin = null;
        final Instant expectedBoughtAt = null;
        final Instant expectedSoldAt = null;

        final var expectedErrorMessage = "'boughtAt' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .boughtAt(expectedBoughtAt)
                .soldAt(expectedSoldAt)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithSoldAtNullAndStatusSELL_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final var expectedSellExecutedQuantity = 1L;
        final BigDecimal expectedBuyExecutedPrice = null;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final ExecutionID expectedOrigin = null;
        final Instant expectedBoughtAt = null;
        final Instant expectedSoldAt = null;

        final var expectedErrorMessage = "'soldAt' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .profitPercentage(expectedProfitPercentage)
                .boughtAt(expectedBoughtAt)
                .soldAt(expectedSoldAt)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullWalletId_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final WalletID expectedWalletId = null;
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'walletId' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenAInvalidNullStatus_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final ExecutionStatus expectedStatus = null;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'status' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithSellExecutedQuantityFilledAndStatusBUY_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final var expectedSellExecutedQuantity = 100L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'sellExecutedQuantity' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithSellExecutedPriceFilledAndStatusBUY_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = BigDecimal.ONE;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'sellExecutedPrice' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithBuyExecutedQuantityFilledAndStatusSELL_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 100L;
        final var expectedSellExecutedQuantity = 55L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final var expectedSellExecutedPrice = BigDecimal.ONE;
        final var expectedStatus = ExecutionStatus.SELL;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'buyExecutedQuantity' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithBuyExecutedPriceFilledAndStatusSELL_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final var expectedSellExecutedQuantity = 55L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = BigDecimal.ONE;
        final var expectedStatus = ExecutionStatus.SELL;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'buyExecutedPrice' should not be filled";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithInvalidNullBuyExecutedQuantity_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final Long expectedSellExecutedQuantity = null;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'buyExecutedQuantity' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithInvalidNullBuyExecutedPrice_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = 100L;
        final Long expectedSellExecutedQuantity = null;
        final BigDecimal expectedBuyExecutedPrice = null;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.BUY;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'buyExecutedPrice' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithInvalidNullSellExecutedQuantity_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = null;
        final Long expectedSellExecutedQuantity = null;
        final BigDecimal expectedBuyExecutedPrice = null;
        final var expectedSellExecutedPrice = BigDecimal.ONE;
        final var expectedStatus = ExecutionStatus.SELL;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'sellExecutedQuantity' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenParamsWithInvalidNullSellExecutedPrice_whenCallsNewExecution_shouldReturnADomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final Long expectedBuyExecutedQuantity = 100L;
        final var expectedSellExecutedQuantity = 30L;
        final BigDecimal expectedBuyExecutedPrice = null;
        final BigDecimal expectedSellExecutedPrice = null;
        final var expectedStatus = ExecutionStatus.SELL;
        final ExecutionID expectedOrigin = null;
        final var expectedErrorMessage = "'sellExecutedPrice' must not be null";

        final var actualException = Assertions.assertThrows(DomainException.class, () -> ExecutionBuilder.create()
                .stockId(expectedStockId)
                .walletId(expectedWalletId)
                .buyExecutedQuantity(expectedBuyExecutedQuantity)
                .sellExecutedQuantity(expectedSellExecutedQuantity)
                .buyExecutedPrice(expectedBuyExecutedPrice)
                .sellExecutedPrice(expectedSellExecutedPrice)
                .status(expectedStatus)
                .origin(expectedOrigin)
                .build());

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }
}
