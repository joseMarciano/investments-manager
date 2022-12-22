package com.investment.managment.wallet.presenter;

import com.investment.managment.wallet.create.CreateWalletCommandOutput;
import com.investment.managment.wallet.models.CreateWalletResponse;

import java.util.function.Function;

public interface WalletAPIPresenter {

    Function<CreateWalletCommandOutput, CreateWalletResponse> present = command ->
            new CreateWalletResponse(
                    command.id().getValue(),
                    command.name(),
                    command.description(),
                    command.color(),
                    command.createdAt(),
                    command.updatedAt()
            );
}
