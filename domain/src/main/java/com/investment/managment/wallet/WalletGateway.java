package com.investment.managment.wallet;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;

import java.util.Optional;

public interface WalletGateway {
    Wallet create(Wallet aWallet);

    Wallet update(Wallet aWallet);

    Optional<Wallet> findById(WalletID anId);

    Pagination<Wallet> findAll(SearchQuery query);

    void deleteById(WalletID id);
}
