package com.investment.managment.wallet.persistence;

import com.investment.managment.DataBaseExtension;
import com.investment.managment.IntegrationTest;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import static com.investment.managment.wallet.WalletBuilder.create;

@IntegrationTest
public class WalletGatewayIT extends DataBaseExtension {

    @Autowired
    private WalletGateway walletGateway;

    @Autowired
    private WalletRepository walletRepository;

    @Test
    public void injectTest() {
        Assertions.assertNotNull(walletGateway);
        Assertions.assertNotNull(walletRepository);
    }

    @Test
    public void givenAValidWallet_whenCallsCreate_shouldPersistIt() {
        final var expectedName = "Wallet";
        final var expectedDescription = "Wallet long term";
        final var expectedColor = "FFFFF";

        final Wallet aWallet = create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        Assertions.assertEquals(0, walletRepository.count());
        final Wallet actualWallet = walletGateway.create(aWallet);
        Assertions.assertEquals(1, walletRepository.count());

        Assertions.assertNotNull(aWallet.getId());
        Assertions.assertEquals(aWallet.getId(), actualWallet.getId());
        Assertions.assertEquals(aWallet.getDescription(), actualWallet.getDescription());
        Assertions.assertEquals(aWallet.getColor(), actualWallet.getColor());
        Assertions.assertNotNull(aWallet.getCreatedAt());
        Assertions.assertEquals(aWallet.getCreatedAt(), actualWallet.getUpdatedAt());
        Assertions.assertEquals(aWallet.getCreatedAt(), actualWallet.getCreatedAt());
        Assertions.assertEquals(aWallet.getUpdatedAt(), actualWallet.getUpdatedAt());

        final WalletJpaEntity walletJpaEntity = walletRepository.findById(aWallet.getId().getValue()).get();

        Assertions.assertNotNull(walletJpaEntity.getId());
        Assertions.assertEquals(WalletID.from(walletJpaEntity.getId()), actualWallet.getId());
        Assertions.assertEquals(walletJpaEntity.getDescription(), actualWallet.getDescription());
        Assertions.assertEquals(walletJpaEntity.getColor(), actualWallet.getColor());
        Assertions.assertNotNull(walletJpaEntity.getCreatedAt());
        Assertions.assertEquals(walletJpaEntity.getCreatedAt().toString(), actualWallet.getUpdatedAt().toString());
        Assertions.assertEquals(walletJpaEntity.getCreatedAt().toString(), actualWallet.getCreatedAt().toString());
        Assertions.assertEquals(walletJpaEntity.getUpdatedAt().toString(), actualWallet.getUpdatedAt().toString());
        Assertions.assertEquals(1, walletRepository.count());

    }

    @Test
    public void givenAValidWallet_whenCallsUpdate_shouldUpdateIt() {
        final var expectedName = "Wallet";
        final var expectedDescription = "Wallet long term";
        final var expectedColor = "FFFFF";
        var aWallet = create()
                .name("wallet")
                .description("Wallet term")
                .color("FFEFF")
                .build();
        final var expectedId = aWallet.getId();

        Assertions.assertEquals(0, walletRepository.count());
        var walletJPAEntity = walletRepository.saveAndFlush(WalletJpaEntity.from(aWallet));
        Assertions.assertEquals(1, walletRepository.count());

        Assertions.assertNotNull(aWallet.getId());
        Assertions.assertEquals(aWallet.getId().getValue(), walletJPAEntity.getId());
        Assertions.assertEquals(aWallet.getDescription(), walletJPAEntity.getDescription());
        Assertions.assertEquals(aWallet.getColor(), walletJPAEntity.getColor());
        Assertions.assertNotNull(aWallet.getCreatedAt());
        Assertions.assertEquals(aWallet.getCreatedAt(), walletJPAEntity.getUpdatedAt());
        Assertions.assertEquals(aWallet.getCreatedAt(), walletJPAEntity.getCreatedAt());
        Assertions.assertEquals(aWallet.getUpdatedAt(), walletJPAEntity.getUpdatedAt());

        aWallet.update(expectedName, expectedDescription, expectedColor);
        final var walletUpdated = walletGateway.update(aWallet);

        Assertions.assertNotNull(walletUpdated.getId());
        Assertions.assertEquals(walletUpdated.getId(), expectedId);
        Assertions.assertEquals(walletUpdated.getDescription(), expectedDescription);
        Assertions.assertEquals(walletUpdated.getColor(), expectedColor);
        Assertions.assertNotNull(walletUpdated.getCreatedAt());
        Assertions.assertNotNull(walletUpdated.getUpdatedAt());
        Assertions.assertTrue(walletUpdated.getCreatedAt().isBefore(walletUpdated.getUpdatedAt()));

        final var actualWallet = walletRepository.findById(aWallet.getId().getValue()).get().toAggregate();
        Assertions.assertNotNull(actualWallet.getId());
        Assertions.assertEquals(actualWallet.getId(), expectedId);
        Assertions.assertEquals(actualWallet.getDescription(), expectedDescription);
        Assertions.assertEquals(actualWallet.getColor(), expectedColor);
        Assertions.assertNotNull(actualWallet.getCreatedAt());
        Assertions.assertNotNull(actualWallet.getUpdatedAt());
        Assertions.assertTrue(actualWallet.getCreatedAt().isBefore(actualWallet.getUpdatedAt()));
    }

    @Test
    public void givenAValidId_whenCallsFindById_shouldReturnIt() {
        final var expectedName = "Wallet";
        final var expectedDescription = "Wallet long term";
        final var expectedColor = "FFFFF";
        var aWallet = create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();
        final var expectedId = aWallet.getId();

        Assertions.assertEquals(0, walletRepository.count());
        walletRepository.saveAndFlush(WalletJpaEntity.from(aWallet));
        Assertions.assertEquals(1, walletRepository.count());

        final var actualWallet = walletGateway.findById(expectedId).get();

        Assertions.assertNotNull(actualWallet.getId());
        Assertions.assertEquals(actualWallet.getId(), expectedId);
        Assertions.assertEquals(actualWallet.getDescription(), expectedDescription);
        Assertions.assertEquals(actualWallet.getColor(), expectedColor);
        Assertions.assertNotNull(actualWallet.getCreatedAt());
        Assertions.assertNotNull(actualWallet.getUpdatedAt());
        Assertions.assertEquals(actualWallet.getCreatedAt(), actualWallet.getUpdatedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsFindById_shouldReturnEmpty() {
        final var anId = WalletID.unique();

        Assertions.assertEquals(0, walletRepository.count());

        final var walletOptional = walletGateway.findById(anId);
        Assertions.assertTrue(walletOptional.isEmpty());
        Assertions.assertEquals(0, walletRepository.count());
    }

    @Test
    public void givenAValidId_whenCallsDeleteById_shouldDeleteIt() {
        final var expectedName = "Wallet";
        final var expectedDescription = "Wallet long term";
        final var expectedColor = "FFFFF";
        var aWallet = create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();
        final var expectedId = aWallet.getId();

        Assertions.assertEquals(0, walletRepository.count());
        walletRepository.saveAndFlush(WalletJpaEntity.from(aWallet));
        Assertions.assertEquals(1, walletRepository.count());

        walletGateway.deleteById(expectedId);

        Assertions.assertEquals(0, walletRepository.count());
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteById_shouldBeOk() {
        final var anId = WalletID.unique();
        Assertions.assertEquals(0, walletRepository.count());
        Assertions.assertDoesNotThrow(() -> walletGateway.deleteById(anId));
        Assertions.assertEquals(0, walletRepository.count());
    }

    @ParameterizedTest
    @CsvSource({
            "0:1:name:asc, 3, A Day trade",
            "0:1:name:desc, 3, Zero lost",
            "0:1:description:asc, 3, Zero lost",
            "0:1:description:desc, 3, É muito bom"
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageCorrectly(final String params, final int expectedTotal, final String expectedName) {
        final var queryParams = params.split(":");
        final var expectedOffset = Integer.valueOf(queryParams[0]);
        final var expectedLimit = Integer.valueOf(queryParams[1]);
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        //TODO: ADICIONAR TESTES PARAMETRIZADOS PARA FACILITAR.
        persistWallets(
                create().name("Zero lost").description("A Day trade is nice").color("D0D0D0").build(),
                create().name("A Day trade").description("The man").color("FFFFF").build(),
                create().name("É muito bom").description("Zero lost is liar").color("D0D0D3").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Wallet> actualPage = walletGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertEquals(actualPage.items().get(0).getName(), expectedName);
    }


    @ParameterizedTest
    @CsvSource({
            "0:1:name:asc, 4, A Day trade",
            "1:1:name:asc, 4, É muito bom",
            "2:1:name:asc, 4, God Day trade",
            "3:1:name:asc, 4, Zero lost"
    })
    public void givenAValidQuery_whenCallsFindAll_shouldPageCorrectly(final String params, final int expectedTotal, final String expectedName) {
        final var queryParams = params.split(":");
        final var expectedOffset = Integer.valueOf(queryParams[0]);
        final var expectedLimit = Integer.valueOf(queryParams[1]);
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        //TODO: ADICIONAR TESTES PARAMETRIZADOS PARA FACILITAR.
        persistWallets(
                create().name("Zero lost").description("A Day trade is nice").color("D0D0D0").build(),
                create().name("A Day trade").description("The man").color("FFFFF").build(),
                create().name("God Day trade").description("The man").color("FFFFF").build(),
                create().name("É muito bom").description("Zero lost is liar").color("D0D0D3").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Wallet> actualPage = walletGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertEquals(actualPage.items().get(0).getName(), expectedName);
    }

    @ParameterizedTest
    @CsvSource({
            "liker, Liker many things, 1",
            "ld, Should I stay or should I go, 1",
            "@, Caracteres especiais: +=12-!@, 1",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageFilteredCorrectly(final String expectedFilter, final String expectedName, final int expectedTotal) {
        final var expectedOffset = 0;
        final var expectedLimit = 20;
        final var expectedSort = "name";
        final var expectedDirection = "desc";

        //TODO: ADICIONAR TESTES PARAMETRIZADOS PARA FACILITAR.
        persistWallets(
                create().name("Liker many things").description("Options nicer").color("D0D0D0").build(),
                create().name("Should I stay or should I go").description("Curious with ç").color("FFFFF").build(),
                create().name("Caracteres especiais: +=12-!@").description("Zero lost is liar").color("D0D0D3").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Wallet> actualPage = walletGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertEquals(actualPage.items().get(0).getName(), expectedName);
    }

    @ParameterizedTest
    @CsvSource({
            "consumer",
            "(mui bien)",
            "there are",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnEmptyPageCorrectly(final String expectedFilter) {
        final var expectedOffset = 0;
        final var expectedLimit = 20;
        final var expectedSort = "name";
        final var expectedDirection = "desc";
        final var expectedTotal = 0;

        //TODO: ADICIONAR TESTES PARAMETRIZADOS PARA FACILITAR.
        persistWallets(
                create().name("Liker many things").description("Options nicer").color("D0D0D0").build(),
                create().name("Should I stay or should I go").description("Curious with ç").color("FFFFF").build(),
                create().name("Caracteres especiais: +=12-!@").description("Zero lost is liar").color("D0D0D3").build()
        );

        final SearchQuery aQuery =
                new SearchQuery(expectedOffset, expectedLimit, expectedSort, expectedDirection, expectedFilter);

        final Pagination<Wallet> actualPage = walletGateway.findAll(aQuery);

        Assertions.assertNotNull(actualPage);
        Assertions.assertEquals(actualPage.total(), expectedTotal);
        Assertions.assertEquals(actualPage.offset(), expectedOffset);
        Assertions.assertEquals(actualPage.limit(), expectedLimit);
        Assertions.assertTrue(actualPage.items().isEmpty());
    }

    private void persistWallets(final Wallet... wallets) {
        for (Wallet wallet : wallets) {
            walletRepository.saveAndFlush(WalletJpaEntity.from(wallet));
        }
    }


}
