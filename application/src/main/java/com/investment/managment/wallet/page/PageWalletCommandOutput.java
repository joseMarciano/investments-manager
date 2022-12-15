package com.investment.managment.wallet.page;

import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletID;

public record PageWalletCommandOutput(
        WalletID id,
        String name,
        String description,
        String color
) {
    public static PageWalletCommandOutput from(final Wallet aWallet) {
        return new PageWalletCommandOutput(
                aWallet.getId(),
                aWallet.getName(),
                aWallet.getDescription(),
                aWallet.getColor()
        );
    }
}
