package com.investment.managment.wallet.update;

import com.investment.managment.UseCase;
import com.investment.managment.validation.exception.NotFoundException;
import com.investment.managment.wallet.Wallet;
import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.WalletID;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class UpdateWalletUseCase extends UseCase<UpdateWalletCommandInput, UpdateWalletCommandOutput> {

    private final WalletGateway walletGateway;

    public UpdateWalletUseCase(final WalletGateway walletGateway) {
        this.walletGateway = requireNonNull(walletGateway);
    }

    @Override
    public UpdateWalletCommandOutput execute(final UpdateWalletCommandInput aCommand) {
        final var anId = aCommand.id();
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var aColor = aCommand.color();

        final var aWallet = walletGateway.findById(anId)
                .orElseThrow(notFoundException(anId));

        return UpdateWalletCommandOutput.from(
                walletGateway.update(aWallet.update(aName, aDescription, aColor))
        );
    }

    private Supplier<NotFoundException> notFoundException(final WalletID anId) {
        return () -> NotFoundException.of(anId, Wallet.class);
    }


}
