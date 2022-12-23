package com.investment.managment.wallet.presenter;

import com.investment.managment.wallet.create.CreateWalletCommandOutput;
import com.investment.managment.wallet.models.CreateWalletResponse;
import com.investment.managment.wallet.models.UpdateWalletResponse;
import com.investment.managment.wallet.update.UpdateWalletCommandOutput;

import java.util.function.Function;

public interface WalletAPIPresenter {

    static CreateWalletResponse present(final CreateWalletCommandOutput command) {
        return new CreateWalletResponse(
                command.id().getValue(),
                command.name(),
                command.description(),
                command.color(),
                command.createdAt(),
                command.updatedAt()
        );
    }

    static UpdateWalletResponse present(final UpdateWalletCommandOutput command) {
        return new UpdateWalletResponse(
                command.id().getValue(),
                command.name(),
                command.description(),
                command.color(),
                command.createdAt(),
                command.updatedAt()
        );
    }
}
