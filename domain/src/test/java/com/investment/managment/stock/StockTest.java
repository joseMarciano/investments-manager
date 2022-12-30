package com.investment.managment.stock;

import com.investment.managment.validation.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class StockTest {

    @Test
    public void givenAValidParams_whenCallsNewStock_shouldInstantiateIt() {
        final var expectedSymbol = "PETR4";

        final var actualStock = StockBuilder.create()
                .symbol(expectedSymbol)
                .build();

        Assertions.assertNotNull(actualStock.getId());
        Assertions.assertEquals(actualStock.getSymbol(), expectedSymbol);
        Assertions.assertNotNull(actualStock.getCreatedAt());
        Assertions.assertNotNull(actualStock.getUpdatedAt());
    }

    @Test
    public void givenAInvalidNullSymbol_whenCallsNewStock_shouldReturnADomainException() {
        final String expectedSymbol = null;

        final var expectedErrorMessage = "'symbol' should not be null";

        final Executable newStockExecutable = () -> StockBuilder.create()
                .symbol(expectedSymbol)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newStockExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidEmptySymbol_whenCallsNewStock_shouldReturnADomainException() {
        final var expectedSymbol = "  ";
        final var expectedErrorMessage = "'symbol' should not be empty";

        final Executable newStockExecutable = () -> StockBuilder.create()
                .symbol(expectedSymbol)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newStockExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAInvalidBigSymbol_whenCallsNewStock_shouldReturnADomainException() {
        final var expectedSymbol = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o 
                desafiador cenário globalizado facilita a criação dos relacionamentos 
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito 
                de como a execução dos pontos do programa possibilita uma melhor visão 
                global do sistema de formação de quadros que corresponde às necessidades.
                """;

        final var expectedErrorMessage = "'symbol' should be between 1 and 20 characters";

        final Executable newStockExecutable = () -> StockBuilder.create()
                .symbol(expectedSymbol)
                .build();

        final var actualException =
                Assertions.assertThrows(DomainException.class, newStockExecutable);

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }


    @Test
    public void givenAValidStock_whenCallsUpdateWithValidParams_shouldUpdateIt() {
        final var expectedSymbol = "PETR4";

        final var aStock = StockBuilder.create()
                .symbol("PTE3")
                .build();

        Assertions.assertEquals(aStock.getCreatedAt(), aStock.getUpdatedAt());
        final var actualStock = aStock.update(expectedSymbol);

        Assertions.assertNotNull(actualStock.getId());
        Assertions.assertEquals(actualStock.getSymbol(), expectedSymbol);
        Assertions.assertNotNull(actualStock.getCreatedAt());
        Assertions.assertTrue(actualStock.getCreatedAt().isBefore(actualStock.getUpdatedAt()));
    }

    @Test
    public void givenAValidStock_whenCallsUpdateWithAInvalidNullSymbol_shouldReturnADomainException() {
        final String expectedSymbol = null;

        final var expectedErrorMessage = "'symbol' should not be null";

        final var aStock = StockBuilder.create()
                .symbol("PETR2")
                .build();

        Assertions.assertEquals(aStock.getCreatedAt(), aStock.getUpdatedAt());

        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> aStock.update(expectedSymbol));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidStock_whenCallsUpdateWithAInvalidEmptySymbol_shouldReturnADomainException() {
        final var expectedSymbol = "  ";
        final var expectedErrorMessage = "'symbol' should not be empty";

        final var aStock = StockBuilder.create()
                .symbol("PTER4")
                .build();

        Assertions.assertEquals(aStock.getCreatedAt(), aStock.getUpdatedAt());

        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> aStock.update(expectedSymbol));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }

    @Test
    public void givenAValidStock_whenCallsUpdateWithAInvalidBigSymbol_shouldReturnADomainException() {
        final var expectedSymbol = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;

        final var expectedErrorMessage = "'symbol' should be between 1 and 20 characters";

        final var aStock = StockBuilder.create()
                .symbol("PETR5")
                .build();

        Assertions.assertEquals(aStock.getCreatedAt(), aStock.getUpdatedAt());

        final var actualException =
                Assertions.assertThrows(DomainException.class,
                        () -> aStock.update(expectedSymbol));

        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
    }
}
