package com.investment.managment.execution.update;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.execution.update.buy.UpdateBuyFieldsExecutionUseCase;
import com.investment.managment.execution.update.sell.UpdateSellFieldsExecutionUseCase;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockGateway;
import com.investment.managment.stock.StockID;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@UseCaseTest
public class UpdateExecutionUseCaseTest {

    @InjectMocks
    private UpdateExecutionUseCase useCase;
    @Mock
    private StockGateway stockGateway;
    @Mock
    private ExecutionGateway executionGateway;
    @Mock
    private UpdateBuyFieldsExecutionUseCase updateBuyFieldsExecutionUseCase;
    @Mock
    private UpdateSellFieldsExecutionUseCase updateSellFieldsExecutionUseCase;


    // 1 -> Quando eu edito e não tem execuções vendidas, pode seguir o fluxo normal
    // 2 -> Quando eu edito e tem execuções vendidas, buscar as execuções e verificar se o numero de ações é menor de que o total já vendido. Se for, exception (ValidationException)
    // 3 -> Só devo deixar atualizar os campos dos respectivos status

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithBUYExecutionStatus_shouldUpdateIt() {
        final var expectedStockId = StockID.unique();
        final var expectedWalletId = WalletID.unique();
        final var expectedBuyExecutedQuantity = 2L;
        final var expectedBuyExecutedPrice = BigDecimal.TEN;
        final var expectedBuyExecutedVolume = BigDecimal.valueOf(20);
        final var expectedStatus = ExecutionStatus.BUY;
        final var expectedProfitPercentage = 5.00;
        final var expectedBoughtAt = Instant.now();
        final var anExecution =
                ExecutionBuilder
                        .create()
                        .stockId(StockID.unique())
                        .walletId(expectedWalletId)
                        .buyExecutedQuantity(6L)
                        .buyExecutedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .boughtAt(Instant.now())
                        .build();

        final var expectedId = anExecution.getId();

        final var expectedOutput = new UpdateExecutionCommandOutput(
                expectedId,
                expectedStockId,
                expectedWalletId,
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBuyExecutedVolume,
                null,
                null,
                null,
                expectedStatus,
                expectedBoughtAt,
                null,
                Instant.now(),
                Instant.now()
        );

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedStockId.getValue(),
                expectedProfitPercentage,
                expectedBuyExecutedQuantity,
                expectedBuyExecutedPrice,
                expectedBoughtAt
        );

        when(stockGateway.findById(eq(expectedStockId)))
                .thenReturn(Optional.of(mock(Stock.class)));
        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(anExecution));
        when(updateBuyFieldsExecutionUseCase.execute(aCommand))
                .thenReturn(expectedOutput);

        final var actualOutput = useCase.execute(aCommand);


        Assertions.assertNotNull(actualOutput.id());
        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.stockId(), expectedStockId);
        Assertions.assertEquals(actualOutput.walletId(), expectedWalletId);
        Assertions.assertEquals(actualOutput.buyExecutedQuantity(), expectedBuyExecutedQuantity);
        Assertions.assertEquals(actualOutput.buyExecutedPrice(), expectedBuyExecutedPrice);
        Assertions.assertEquals(actualOutput.buyExecutedVolume(), expectedBuyExecutedVolume);
        Assertions.assertNull(actualOutput.sellExecutedQuantity());
        Assertions.assertNull(actualOutput.sellExecutedPrice());
        Assertions.assertEquals(actualOutput.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(actualOutput.boughtAt(), expectedBoughtAt);
        Assertions.assertNull(actualOutput.soldAt());
        Assertions.assertEquals(actualOutput.status(), expectedStatus);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
        Assertions.assertTrue(actualOutput.updatedAt().isAfter(actualOutput.createdAt()));
        verify(updateBuyFieldsExecutionUseCase).execute(aCommand);
        verify(updateSellFieldsExecutionUseCase, times(0)).execute(any());
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateUseCaseWithSELLExecutionStatus_shouldUpdateIt() {
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
                        .stockId(StockID.unique())
                        .walletId(expectedWalletId)
                        .sellExecutedQuantity(6L)
                        .sellExecutedPrice(BigDecimal.ONE)
                        .status(expectedStatus)
                        .profitPercentage(8.00)
                        .soldAt(Instant.now())
                        .build();
        final var expectedId = anExecution.getId();

        final var expectedOutput = new UpdateExecutionCommandOutput(
                expectedId,
                expectedStockId,
                expectedWalletId,
                expectedProfitPercentage,
                null,
                null,
                null,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSellExecutedVolume,
                expectedStatus,
                null,
                expectedSoldAt,
                Instant.now(),
                Instant.now()
        );


        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedStockId.getValue(),
                expectedProfitPercentage,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSoldAt
        );

        when(stockGateway.findById(eq(expectedStockId)))
                .thenReturn(Optional.of(mock(Stock.class)));
        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(anExecution));
        when(updateSellFieldsExecutionUseCase.execute(aCommand))
                .thenReturn(expectedOutput);

        final var actualOutput = useCase.execute(aCommand);


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
        verify(updateSellFieldsExecutionUseCase).execute(aCommand);
        verify(updateBuyFieldsExecutionUseCase, times(0)).execute(any());
    }

    @Test
    public void givenAInvalidCommandWithInvalidStockId_whenCallsUpdateUseCase_shouldReturnNotFoundException() {
        final var expectedStockId = StockID.unique();
        final var expectedSellExecutedQuantity = 2L;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 5.00;
        final var expectedSoldAt = Instant.now();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted("Stock", expectedStockId.getValue());
        final var expectedId = ExecutionID.unique();

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedStockId.getValue(),
                expectedProfitPercentage,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSoldAt
        );

        when(stockGateway.findById(any()))
                .thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
        verify(updateSellFieldsExecutionUseCase, times(0)).execute(any());
        verify(updateBuyFieldsExecutionUseCase, times(0)).execute(any());
    }

    @Test
    public void givenAInvalidCommandWithInvalidWalletId_whenCallsUpdateUseCase_shouldReturnNotFoundException() {
        final var expectedStockId = StockID.unique();
        final var expectedSellExecutedQuantity = 2L;
        final var expectedSellExecutedPrice = BigDecimal.TEN;
        final var expectedProfitPercentage = 5.00;
        final var expectedSoldAt = Instant.now();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted("Stock", expectedStockId.getValue());
        final var expectedId = ExecutionID.from("invalid-id");

        final var aCommand = new UpdateExecutionCommandInput(
                expectedId.getValue(),
                expectedStockId.getValue(),
                expectedProfitPercentage,
                expectedSellExecutedQuantity,
                expectedSellExecutedPrice,
                expectedSoldAt
        );

        when(stockGateway.findById(any()))
                .thenReturn(Optional.empty());


        final var actualException = Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
        verify(updateSellFieldsExecutionUseCase, times(0)).execute(any());
        verify(updateBuyFieldsExecutionUseCase, times(0)).execute(any());
    }

}
