package com.investment.managment.wallet.deleteById;

import com.investment.managment.UseCaseTest;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@UseCaseTest
public class DeleteWalletByIdUseCaseTest {

    @InjectMocks
    private DeleteWalletByIdUseCase useCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidID_whenCallsUseCase_shouldBeOk() {
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

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        Mockito.verify(walletGateway).deleteById(eq(expectedId));
    }

    @Test
    public void givenAnInvalidID_whenCallsUseCase_shouldBeOk() {
        final var expectedId = WalletID.unique();

        when(walletGateway.findById(eq(expectedId)))
                .thenReturn(Optional.empty());


        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        Mockito.verify(walletGateway, times(0)).deleteById(eq(expectedId));
    }
}
