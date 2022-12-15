package com.investment.managment.wallet.findById;

import com.investment.managment.UseCase;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;

import java.util.function.Supplier;

public class FindWalletByIdUseCase extends UseCase<WalletID, FindWalletByIdCommandOutput> {

    private final WalletGateway walletGateway;

    public FindWalletByIdUseCase(final WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    @Override
    public FindWalletByIdCommandOutput execute(final WalletID anId) {
        return walletGateway.findById(anId)
                .map(FindWalletByIdCommandOutput::from)
                .orElseThrow(notFoundException(anId));
    }

    private Supplier<NotFoundException> notFoundException(final WalletID anId) {
        return () -> NotFoundException.of(anId, Wallet.class);
    }
}
