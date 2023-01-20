package com.investment.managment.execution.deleteById;

import com.investment.managment.UseCaseTest;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.validation.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@UseCaseTest
public class DeleteExecutionByIdUseCaseTest {

    @InjectMocks
    private DeleteExecutionByIdUseCase useCase;

    @Mock
    private ExecutionGateway executionGateway;


    @Test
    public void givenAValidID_whenCallsUseCase_shouldBeOk() {
        final var expectedId = ExecutionID.unique();

        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(mock(Execution.class)));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        Mockito.verify(executionGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAnInvalidID_whenCallsUseCase_shouldBeOk() {
        final var expectedId = ExecutionID.unique();

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        Mockito.verify(executionGateway, times(0)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidID_whenCallsUseCaseWithSoldExecutions_shouldReturnDomainException() {
        final var expectedId = ExecutionID.unique();
        final var expectedErrorMessage = "There are others executions sold through this execution";

        when(executionGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(mock(Execution.class)));

        when(executionGateway.existsByOriginId(eq(expectedId)))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(expectedId));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
        Mockito.verify(executionGateway, times(0)).deleteById(any());
    }
}
