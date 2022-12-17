package com.investment.managment.wallet.persistence;

import com.investment.managment.DataBaseExtension;
import com.investment.managment.IntegrationTest;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

        final Wallet aWallet = WalletBuilder.create()
                .name(expectedName)
                .description(expectedDescription)
                .color(expectedColor)
                .build();

        final Wallet actualWallet = walletGateway.create(aWallet);

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
    }


}
