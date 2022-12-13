package com.investment.managment.wallet.findById;

import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletID;

import java.time.Instant;

public record FindWalletByIdCommandOutput(
        WalletID id,
        String name,
        String description,
        String color,
        Instant createdAt,
        Instant updatedAt
) {
    public static FindWalletByIdCommandOutput from(final Wallet aWallet) {
        return new FindWalletByIdCommandOutput(
                aWallet.getId(),
                aWallet.getName(),
                aWallet.getDescription(),
                aWallet.getColor(),
                aWallet.getCreatedAt(),
                aWallet.getUpdatedAt()
        );
    }
}
