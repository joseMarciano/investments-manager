package com.investment.managment.wallet.findById;

import com.investment.managment.UseCaseTest;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@UseCaseTest
public class FindWalletByIdUseCaseTest {

    @InjectMocks
    private FindWalletByIdUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidID_whenCallsUseCase_shouldReturnWallet() {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = "THis is long term description";
        final var expectedColor = "FFFFFF";
        final var aWallet = WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();
        final var expectedId = aWallet.getId();

        when(walletGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(aWallet));

        final var actualOutput = useCase.execute(expectedId);

        Assertions.assertEquals(actualOutput.id(), expectedId);
        Assertions.assertEquals(actualOutput.name(), expectedName);
        Assertions.assertEquals(actualOutput.description(), expectedDescription);
        Assertions.assertEquals(actualOutput.color(), expectedColor);
        Assertions.assertNotNull(actualOutput.createdAt());
        Assertions.assertNotNull(actualOutput.updatedAt());
    }

    @Test
    public void givenAnInvalidID_whenCallsUseCase_shouldReturnANotFoundException() {
        final var expectedId = WalletID.unique();
        final var expectedErrorMessage = "Entity %s with identifier %s was not found".formatted(expectedId.getValue(), "Wallet");

        when(walletGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(expectedId));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}
