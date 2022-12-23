package com.investment.managment.wallet.presenter;

import com.investment.managment.wallet.create.CreateWalletCommandOutput;
import com.investment.managment.wallet.findById.FindWalletByIdCommandOutput;
import com.investment.managment.wallet.models.CreateWalletResponse;
import com.investment.managment.wallet.models.FindByIdWalletResponse;
import com.investment.managment.wallet.models.PageWalletResponse;
import com.investment.managment.wallet.models.UpdateWalletResponse;
import com.investment.managment.wallet.page.PageWalletCommandOutput;
import com.investment.managment.wallet.update.UpdateWalletCommandOutput;

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

    static FindByIdWalletResponse present(final FindWalletByIdCommandOutput command) {
        return new FindByIdWalletResponse(
                command.id().getValue(),
                command.name(),
                command.description(),
                command.color(),
                command.createdAt(),
                command.updatedAt()
        );
    }

    static PageWalletResponse present(PageWalletCommandOutput command) {
        return new PageWalletResponse(
                command.id().getValue(),
                command.name(),
                command.description(),
                command.color()
        );
    }
}
