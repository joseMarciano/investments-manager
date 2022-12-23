package com.investment.managment.api.wallet;

import com.investment.managment.api.WalletAPI;
import com.investment.managment.wallet.create.CreateWalletCommandInput;
import com.investment.managment.wallet.create.CreateWalletUseCase;
import com.investment.managment.wallet.models.CreateWalletRequest;
import com.investment.managment.wallet.models.CreateWalletResponse;
import com.investment.managment.wallet.models.UpdateWalletRequest;
import com.investment.managment.wallet.models.UpdateWalletResponse;
import com.investment.managment.wallet.presenter.WalletAPIPresenter;
import com.investment.managment.wallet.update.UpdateWalletCommandInput;
import com.investment.managment.wallet.update.UpdateWalletUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController implements WalletAPI {

    private final CreateWalletUseCase createWalletUseCase;
    private final UpdateWalletUseCase updateWalletUseCase;

    public WalletController(final CreateWalletUseCase createWalletUseCase, final UpdateWalletUseCase updateWalletUseCase) {
        this.createWalletUseCase = createWalletUseCase;
        this.updateWalletUseCase = updateWalletUseCase;
    }

    @Override
    public CreateWalletResponse create(final CreateWalletRequest walletRequest) {
        final var aCommand = CreateWalletCommandInput.with(
                walletRequest.name(),
                walletRequest.description(),
                walletRequest.color()
        );

        return WalletAPIPresenter.present(createWalletUseCase.execute(aCommand));
    }

    @Override
    public UpdateWalletResponse update(final String id, final UpdateWalletRequest walletRequest) {
        final var aCommand = UpdateWalletCommandInput.with(
                walletRequest.id(),
                walletRequest.name(),
                walletRequest.description(),
                walletRequest.color()
        );

        return WalletAPIPresenter.present(updateWalletUseCase.execute(aCommand));
    }


}
