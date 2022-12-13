package com.investment.managment.wallet.update;

import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletID;

import java.time.Instant;

public record UpdateWalletCommandOutput(
        WalletID id,
        String name,
        String description,
        String color,
        Instant createdAt,
        Instant updatedAt
) {
    public static UpdateWalletCommandOutput from(final Wallet aWallet) {
        return new UpdateWalletCommandOutput(
                aWallet.getId(),
                aWallet.getName(),
                aWallet.getDescription(),
                aWallet.getColor(),
                aWallet.getCreatedAt(),
                aWallet.getUpdatedAt()
        );
    }
}
