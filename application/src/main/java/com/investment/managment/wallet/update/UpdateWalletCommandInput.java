package com.investment.managment.wallet.update;

import com.investment.managment.wallet.WalletID;

public record UpdateWalletCommandInput(
        WalletID id,
        String name,
        String description,
        String color
) {

    public static UpdateWalletCommandInput with(final String id, final String name, final String description, final String color) {
        return new UpdateWalletCommandInput(WalletID.from(id), name, description, color);
    }
}
