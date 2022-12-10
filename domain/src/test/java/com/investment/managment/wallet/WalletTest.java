package com.investment.managment.wallet;

import com.investment.managment.validation.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class WalletTest {

    @Test
    public void givenAValidParams_whenCallsNewWallet_shouldInstantiateIt() {
        final var expectedName = "Long term";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var actualWallet = WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        Assertions.assertNotNull(actualWallet.getId());
        Assertions.assertEquals(actualWallet.getName(), expectedName);
        Assertions.assertEquals(actualWallet.getDescription(), expectedDescription);
        Assertions.assertEquals(actualWallet.getColor(), expectedColor);
        Assertions.assertNotNull(actualWallet.getCreatedAt());
        Assertions.assertNotNull(actualWallet.getUpdatedAt());
    }

    @Test
    public void givenAInvalidNullName_whenCallsNewWallet_shouldReturnADomainException() {
        final String expectedName = null;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should not be null";

        final Executable newWalletExecutable = () -> WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newWalletExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsNewWallet_shouldReturnADomainException() {
        final var expectedName = "  ";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should not be empty";

        final Executable newWalletExecutable = () -> WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newWalletExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidBigName_whenCallsNewWallet_shouldReturnADomainException() {
        final var expectedName = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o 
                desafiador cenário globalizado facilita a criação dos relacionamentos 
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito 
                de como a execução dos pontos do programa possibilita uma melhor visão 
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should be between 1 and 100 characters";

        final Executable newWalletExecutable = () -> WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newWalletExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidBigDescription_whenCallsNewWallet_shouldReturnADomainException() {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito 
                de como a execução dos pontos do programa possibilita uma melhor visão 
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'description' should be between 1 and 255 characters";

        final Executable newWalletExecutable = () -> WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newWalletExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}
