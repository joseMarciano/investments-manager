package com.investment.managment.api.wallet;

import com.investment.managment.api.WalletAPI;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.wallet.WalletID;
import com.investment.managment.wallet.create.CreateWalletCommandInput;
import com.investment.managment.wallet.create.CreateWalletUseCase;
import com.investment.managment.wallet.deleteById.DeleteWalletByIdUseCase;
import com.investment.managment.wallet.findById.FindWalletByIdUseCase;
import com.investment.managment.wallet.models.*;
import com.investment.managment.wallet.page.PageWalletUseCase;
import com.investment.managment.wallet.presenter.WalletAPIPresenter;
import com.investment.managment.wallet.update.UpdateWalletCommandInput;
import com.investment.managment.wallet.update.UpdateWalletUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController implements WalletAPI {

    private final CreateWalletUseCase createWalletUseCase;
    private final UpdateWalletUseCase updateWalletUseCase;
    private final DeleteWalletByIdUseCase deleteWalletByIdUseCase;
    private final FindWalletByIdUseCase findWalletByIdUseCase;
    private final PageWalletUseCase pageWalletUseCase;

    public WalletController(final CreateWalletUseCase createWalletUseCase,
                            final UpdateWalletUseCase updateWalletUseCase,
                            final DeleteWalletByIdUseCase deleteWalletByIdUseCase,
                            final FindWalletByIdUseCase findWalletByIdUseCase,
                            final PageWalletUseCase pageWalletUseCase) {
        this.createWalletUseCase = createWalletUseCase;
        this.updateWalletUseCase = updateWalletUseCase;
        this.deleteWalletByIdUseCase = deleteWalletByIdUseCase;
        this.findWalletByIdUseCase = findWalletByIdUseCase;
        this.pageWalletUseCase = pageWalletUseCase;
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

    @Override
    public void deleteById(final String id) {
        deleteWalletByIdUseCase.execute(WalletID.from(id));
    }

    @Override
    public FindByIdWalletResponse findById(final String id) {
        return WalletAPIPresenter.present(findWalletByIdUseCase.execute(WalletID.from(id)));
    }

    @Override
    public Pagination<PageWalletResponse> page(final int limit,
                                               final int offset,
                                               final String filter,
                                               final String sort,
                                               final String direction) {

        final var searchQuery = new SearchQuery(offset,
                limit,
                sort,
                direction,
                filter);

        return pageWalletUseCase.execute(searchQuery).map(WalletAPIPresenter::present);
    }
}
