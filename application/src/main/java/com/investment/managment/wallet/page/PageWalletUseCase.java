package com.investment.managment.wallet.page;

import com.investment.managment.UseCase;
import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.wallet.WalletGateway;

public class PageWalletUseCase extends UseCase<SearchQuery, Pagination<PageWalletCommandOutput>> {

    private final WalletGateway walletGateway;

    public PageWalletUseCase(final WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    @Override
    public Pagination<PageWalletCommandOutput> execute(final SearchQuery searchQuery) {
        return walletGateway.findAll(searchQuery)
                .map(PageWalletCommandOutput::from);
    }
}
