package com.investment.managment.wallet;

import java.util.Optional;

public interface WalletGateway {
    Wallet create(Wallet aWallet);
    Wallet update(Wallet aWallet);
    Optional<Wallet> findById(WalletID anId);
}
