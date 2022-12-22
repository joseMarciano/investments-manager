package com.investment.managment.api.wallet;

import com.investment.managment.api.WalletAPI;
import com.investment.managment.wallet.create.CreateWalletCommandInput;
import com.investment.managment.wallet.create.CreateWalletUseCase;
import com.investment.managment.wallet.models.CreateWalletRequest;
import com.investment.managment.wallet.models.CreateWalletResponse;
import com.investment.managment.wallet.presenter.WalletAPIPresenter;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController implements WalletAPI {

    private final CreateWalletUseCase createWalletUseCase;

    public WalletController(final CreateWalletUseCase createWalletUseCase) {
        this.createWalletUseCase = createWalletUseCase;
    }

    @Override
    public CreateWalletResponse create(final CreateWalletRequest walletRequest) {
        final var aCommand = CreateWalletCommandInput.with(
                walletRequest.name(),
                walletRequest.description(),
                walletRequest.color()
        );

        return WalletAPIPresenter.present.apply(createWalletUseCase.execute(aCommand));
    }
}
