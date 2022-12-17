package com.investment.managment.wallet;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.wallet.persistence.WalletJpaEntity;
import com.investment.managment.wallet.persistence.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WalletGatewayImpl implements WalletGateway {

    private final WalletRepository walletRepository;

    public WalletGatewayImpl(final WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public Wallet create(final Wallet aWallet) {
        return save(aWallet);
    }


    @Override
    public Wallet update(final Wallet aWallet) {
        return save(aWallet);
    }

    @Override
    public Optional<Wallet> findById(final WalletID anId) {
        return Optional.empty();
    }

    @Override
    public Pagination<Wallet> findAll(final SearchQuery query) {
        return null;
    }

    @Override
    public void deleteById(final WalletID id) {

    }

    private Wallet save(final Wallet aWallet) {
        final WalletJpaEntity aWalletJpaEntity = walletRepository.save(WalletJpaEntity.from(aWallet));
        return aWalletJpaEntity.toAggregate();
    }
}
