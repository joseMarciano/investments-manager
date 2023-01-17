package com.investment.managment.execution.sell;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionBuilder;
import com.investment.managment.execution.ExecutionGateway;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.util.InstantUtil;
import com.investment.managment.validation.exception.DomainException;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@UseCaseTest
public class SellExecutionUseCaseTest {

    @InjectMocks
    private SellExecutionUseCase useCase;

    @Mock
    private ExecutionGateway executionGateway;

    @Test
    public void givenAValidParam_whenCallsSellUseCase_shouldSellCorrectly() {
        final var originExecution = buildOriginExecution();
        final var expectedStockId = originExecution.getStockId();
        final var expectedOriginId = originExecution.getId();
        final var expectedWalletId = originExecution.getWalletId();
        final var expectedProfitPercentage = originExecution.getProfitPercentage();
        final var expectedExecutedQuantity = 17L;
        final var expectedExecutedPrice = BigDecimal.valueOf(22);
        final var expectedExecutedVolume = BigDecimal.valueOf(374);
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedStatus = ExecutionStatus.SELL;

        final var aCommand = new SellExecutionCommandInput(
                expectedOriginId,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(this.executionGateway.findById(originExecution.getId()))
                .thenReturn(Optional.of(originExecution));

        when(this.executionGateway.create(any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var output = useCase.execute(aCommand);

        Assertions.assertNotNull(output.id());
        Assertions.assertEquals(output.originId(), expectedOriginId);
        Assertions.assertEquals(output.stockId(), expectedStockId);
        Assertions.assertEquals(output.walletId(), expectedWalletId);
        Assertions.assertEquals(output.profitPercentage(), expectedProfitPercentage);
        Assertions.assertEquals(output.executedQuantity(), expectedExecutedQuantity);
        Assertions.assertEquals(output.executedPrice(), expectedExecutedPrice);
        Assertions.assertEquals(output.executedVolume(), expectedExecutedVolume);
        Assertions.assertEquals(output.status(), expectedStatus);
        Assertions.assertEquals(output.executedAt(), expectedExecutedAt);
        Assertions.assertNotNull(output.createdAt());
        Assertions.assertNotNull(output.updatedAt());
        Assertions.assertEquals(output.updatedAt(), output.createdAt());
    }

    @Test
    public void givenInvalidExecutedQuantity_whenCallsSellUseCase_shouldReturnDomainException() {
        final var originExecution = buildOriginExecution();
        final var expectedOriginId = originExecution.getId();
        final var expectedExecutedQuantity = 21L;
        final var expectedExecutedPrice = BigDecimal.valueOf(22);
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedErrorMessage = "'executedQuantity' can not be greater than 20";

        final var aCommand = new SellExecutionCommandInput(
                expectedOriginId,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(this.executionGateway.findById(originExecution.getId()))
                .thenReturn(Optional.of(originExecution));


        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    @Test
    public void givenInvalidExecutedQuantity_whenCallsSellUseCaseWithOthersSoldExecutions_shouldReturnDomainException() {
        final var originExecution = buildOriginExecution();
        final var expectedOriginId = originExecution.getId();
        final var expectedExecutedQuantity = 10L;
        final var expectedExecutedPrice = BigDecimal.valueOf(22);
        final var expectedExecutedAt = InstantUtil.now();
        final var expectedErrorMessage = "'executedQuantity' can not be greater than 9";

        final var aCommand = new SellExecutionCommandInput(
                expectedOriginId,
                expectedExecutedQuantity,
                expectedExecutedPrice,
                expectedExecutedAt
        );

        when(this.executionGateway.findById(originExecution.getId()))
                .thenReturn(Optional.of(originExecution));

        final var mockSoldExecution = mockSoldExecution();
        when(this.executionGateway.findAllByOriginId(eq(originExecution.getId())))
                .thenReturn(List.of(mockSoldExecution));

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(actualException.getError().message(), expectedErrorMessage);
    }

    private Execution mockSoldExecution() {
        final var executionMocked = mock(Execution.class);
        when(executionMocked.getExecutedQuantity()).thenReturn(11L);
        return executionMocked;
    }

    private Execution buildOriginExecution() {
        return ExecutionBuilder.create()
                .walletId(WalletID.unique())
                .stockId(StockID.unique())
                .profitPercentage(5.05)
                .executedQuantity(20L)
                .status(ExecutionStatus.BUY)
                .executedPrice(BigDecimal.TEN)
                .executedAt(InstantUtil.now())
                .build();
    }
}
