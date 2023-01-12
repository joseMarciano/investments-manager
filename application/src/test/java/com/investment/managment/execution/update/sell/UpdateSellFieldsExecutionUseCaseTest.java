package com.investment.managment.execution.update.sell;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.update.UpdateExecutionCommandInput;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@UseCaseTest
public class UpdateSellFieldsExecutionUseCaseTest {

    @InjectMocks
    private UpdateSellFieldsExecutionUseCase useCase;
    @Mock
    private ExecutionGateway executionGateway;

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCase_shouldUpdateIt() {
        final var expectedOriginId = ExecutionID.unique();
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedSellExecutedQuantity = 2L;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedSellExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final var expectedSoldAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .origin(expectedOriginId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .sellExecutedQuantity(6L)
                        .sellExecutedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .soldAt(Instant.now())
                        .build();

        final var expectedId = anExecution.getId();

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSoldAt
        );

        when(executionGateway.findById(expectedOriginId))
                .thenReturn(Optional.of(ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .buyExecutedQuantity(10L)
                        .buyExecutedPrice(BigDecimal.ONE)
                        .status(ExecutionStatus.BUY)
                        .profitPercentage(8.00)
                        .boughtAt(Instant.now())
                        .build()));
        when(executionGateway.update(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand, anExecution);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
        Assertions.assertEquals(actualOutput.sellExecutedQuantity(), expectedSellExecutedQuantity);
        Assertions.assertEquals(actualOutput.sellExecutedPrice(), expectedSellExecutedPrice);
        Assertions.assertEquals(actualOutput.sellExecutedVolume(), expectedSellExecutedVolume);
        Assertions.assertNull(actualOutput.buyExecutedQuantity());
        Assertions.assertNull(actualOutput.buyExecutedPrice());
        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualOutput.soldAt(), expectedSoldAt);
        Assertions.assertNull(actualOutput.boughtAt());
        Assertions.assertEquals(actualOutput.status(), expectedStatus);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithInvalidExecutedQuantity_shouldReturnDomainException() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedOriginId = ExecutionID.unique();
        final var expectedSellExecutedQuantity = 17L;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedStatus = ExecutionStatus.SELL;
        final var expectedProfitPercentage = 5.00;
        final var expectedSoldAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .origin(expectedOriginId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .sellExecutedQuantity(2L)
                        .sellExecutedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .soldAt(Instant.now())
                        .build();


        final var expectedId = anExecution.getId();
        final var expectedErrorMessage = "'sellExecutedPrice' should not be greater than 3";

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedProfitPercentage,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSoldAt
        );

        when(executionGateway.findById(expectedOriginId))
                .thenReturn(Optional.of(ExecutionBuilder
                        .create()
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .buyExecutedQuantity(10L)
                        .buyExecutedPrice(BigDecimal.ONE)
                        .status(ExecutionStatus.BUY)
                        .profitPercentage(8.00)
                        .boughtAt(Instant.now())
                        .build()));

        when(executionGateway.findAllByOriginId(expectedOriginId))
                .thenReturn(List.of(ExecutionBuilder
                        .create()
                        .origin(expectedOriginId)
                        .stockId(expectedStockId)
                        .walletId(expectedWalletId)
                        .sellExecutedQuantity(7L)
                        .sellExecutedPrice(BigDecimal.TEN)
                        .status(expectedStatus)
                        .profitPercentage(7.00)
                        .soldAt(Instant.now())
                        .build(), anExecution));

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand, anExecution));


        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());


    }
//
//    @Test
//    public void givenAValidCommand_whenCallsUpdateUseCaseWithExecutionsSoldAndInvalidExecutedQuantity_shouldUpdateIt() {
//        final var expectedStockId = StockID.unique();
//        final var expectedWalletId = WalletID.unique();
//        final var expectedSellExecutedQuantity = 7L;
//        final var expectedSellExecutedPrice = BigDecimal.TEN;
//        final var expectedSellExecutedVolume = BigDecimal.valueOf(70);
//        final var expectedStatus = ExecutionStatus.BUY;
//        final var expectedProfitPercentage = 5.00;
//        final var expectedSoldAt = Instant.now();
//        final var anExecution =
//                ExecutionBuilder
//                        .create()
//                        .stockId(expectedStockId)
//                        .walletId(expectedWalletId)
//                        .sellExecutedQuantity(6L)
//                        .sellExecutedPrice(BigDecimal.ONE)
//                        .status(expectedStatus)
//                        .profitPercentage(8.00)
//                        .boughtAt(Instant.now())
//                        .build();
//
//        final var expectedId = anExecution.getId();
//        final var aCommand = new UpdateExecutionCommandInput(
//                expectedId.getValue(),
//                expectedProfitPercentage,
//                expectedSellExecutedQuantity,
//                expectedSellExecutedPrice,
//                expectedSoldAt
//        );
//
//
//        when(executionGateway.update(any()))
//                .thenAnswer(AdditionalAnswers.returnsFirstArg());
//        when(executionGateway.existsByOriginId(expectedId))
//                .thenReturn(false);
//
//        final var actualOutput = useCase.execute(aCommand, anExecution);
//
//
//        Assertions.assertNotNull(actualOutput.id());
//        Assertions.assertEquals(actualOutput.id(), expectedId);
//        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
//        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
//        Assertions.assertEquals(actualOutput.sellExecutedQuantity(), expectedSellExecutedQuantity);
//        Assertions.assertEquals(actualOutput.sellExecutedPrice(), expectedSellExecutedPrice);
//        Assertions.assertEquals(actualOutput.sellExecutedVolume(), expectedSellExecutedVolume);
//        Assertions.assertNull(actualOutput.sellExecutedQuantity());
//        Assertions.assertNull(actualOutput.sellExecutedPrice());
//        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
//        Assertions.assertEquals(actualOutput.boughtAt(), expectedSoldAt);
//        Assertions.assertNull(actualOutput.soldAt());
//        Assertions.assertEquals(actualOutput.status(), expectedStatus);
//        Assertions.assertNotNull(actualOutput.createdAt());
//        Assertions.assertNotNull(actualOutput.updatedAt());
//        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
//
//
//    }
}
