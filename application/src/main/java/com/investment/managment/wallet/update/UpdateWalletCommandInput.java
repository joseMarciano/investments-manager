package com.investment.managment.wallet.update;

import com.investment.managment.wallet.WalletID;

public record UpdateWalletCommandInput(
        WalletID id,
        String name,
        String description,
        String color
) {
}
