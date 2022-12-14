package com.investment.managment.wallet.deleteById;

import com.investment.managment.UnitUseCase;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;

public class DeleteWalletByIdUseCase extends UnitUseCase<WalletID> {

    private final WalletGateway walletGateway;

    public DeleteWalletByIdUseCase(final WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    @Override
    public void execute(final WalletID walletID) {
        walletGateway.findById(walletID)
                .ifPresent(wallet -> walletGateway.deleteById(wallet.getId()));
    }
}
