package com.investment.managment.wallet.page;

import com.investment.managment.UseCaseTest;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@UseCaseTest
public class PageWalletUseCaseTest {

    @InjectMocks
    private PageWalletUseCase pageWalletUseCase;

    @Mock
    private WalletGateway walletGateway;

    @Test
    public void givenAValidParams_whenCallsUseCase_shouldReturnFilledPage() {
        final var expectedOffset = 0;
        final var expectedLimit = 20;
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTerm = "lon";
        final var expectedTotal = 1;
        final var aSearchQuery = new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedTerm);

        final var aWallet = WalletBuilder.create()
                .name("long term")
                .description("long term description")
                .color("FFFFF")
                .build();

        when(walletGateway.findAll(eq(aSearchQuery)))
                .thenReturn(new Pagination<>(expectedOffset, expectedLimit, expectedTotal, List.of(aWallet)));

        final var actualOutput = pageWalletUseCase.execute(aSearchQuery);

        Assertions.assertEquals(actualOutput.limit(), expectedLimit);
        Assertions.assertEquals(actualOutput.offset(), expectedOffset);
        Assertions.assertEquals(actualOutput.total(), expectedTotal);
        Assertions.assertEquals(actualOutput.items().get(0).id(), aWallet.getId());
        Assertions.assertEquals(actualOutput.items().get(0).description(), aWallet.getDescription());
        Assertions.assertEquals(actualOutput.items().get(0).color(), aWallet.getColor());
    }

    @Test
    public void givenAValidParams_whenCallsUseCase_shouldReturnEmptyPage() {
        final var expectedOffset = 10;
        final var expectedLimit = 20;
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedTerm = "lon";
        final var expectedTotal = 0;
        final var aSearchQuery = new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedTerm);

        when(walletGateway.findAll(eq(aSearchQuery)))
                .thenReturn(new Pagination<>(expectedOffset, expectedLimit, expectedTotal, Collections.emptyList()));

        final var actualOutput = pageWalletUseCase.execute(aSearchQuery);

        Assertions.assertEquals(actualOutput.limit(), expectedLimit);
        Assertions.assertEquals(actualOutput.offset(), expectedOffset);
        Assertions.assertEquals(actualOutput.total(), expectedTotal);
        Assertions.assertTrue(actualOutput.items().isEmpty());
    }
}
