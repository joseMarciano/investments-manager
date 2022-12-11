package com.investment.managment.wallet.create;

import com.investment.managment.UseCase;
import com.investment.managment.wallet.WalletBuilder;
import com.investment.managment.wallet.WalletGateway;

import static java.util.Objects.requireNonNull;

public class CreateWalletUseCase extends UseCase<CreateWalletCommandInput, CreateWalletCommandOutput> {

    private final WalletGateway walletGateway;

    public CreateWalletUseCase(final WalletGateway walletGateway) {
        this.walletGateway = requireNonNull(walletGateway);
    }

    @Override
    public CreateWalletCommandOutput execute(final CreateWalletCommandInput aCommand) {
        final var aWallet = WalletBuilder.create()
                .name(aCommand.name())
                .description(aCommand.description())
                .color(aCommand.color())
                .build();

        return CreateWalletCommandOutput.from(walletGateway.create(aWallet));
    }
}
