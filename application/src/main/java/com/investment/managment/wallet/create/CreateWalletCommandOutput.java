package com.investment.managment.wallet.create;

import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletID;

import java.time.Instant;

public record CreateWalletCommandOutput(
        WalletID id,
        String name,
        String description,
        String color,
        Instant createdAt,
        Instant updatedAt
) {
    public static CreateWalletCommandOutput from(final Wallet aWallet) {
        return new CreateWalletCommandOutput(
                aWallet.getId(),
                aWallet.getName(),
                aWallet.getDescription(),
                aWallet.getColor(),
                aWallet.getCreatedAt(),
                aWallet.getUpdatedAt()
        );
    }
}
