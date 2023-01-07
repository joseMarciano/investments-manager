package com.investment.managment.wallet;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.util.PaginationUtil;
import com.investment.managment.util.SpecificationUtil;
import com.investment.managment.wallet.persistence.WalletJpaEntity;
import com.investment.managment.wallet.persistence.WalletRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Set.*;

@Component
public class WalletPostgresGatewayImpl implements WalletGateway {

    private final WalletRepository walletRepository;

    public WalletPostgresGatewayImpl(final WalletRepository walletRepository) {
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
        return walletRepository.findById(anId.getValue()).map(WalletJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Wallet> findAll(final SearchQuery query) {
        final var walletSpecification = Optional.ofNullable(query.filter())
                .map(filter -> SpecificationUtil.<WalletJpaEntity>like(
                        of("name", "description"), filter
                )).orElse(null);

        Page<WalletJpaEntity> page = walletRepository.findAll(
                walletSpecification,
                PaginationUtil.buildPage(query)
        );
        return new Pagination<>(
                query.offset(),
                query.limit(),
                page.getTotalElements(),
                page.getContent()
        ).map(WalletJpaEntity::toAggregate);
    }

    @Override
    public void deleteById(final WalletID anId) {
        this.findById(anId)
                .map(Wallet::getId)
                .map(WalletID::getValue)
                .ifPresent(walletRepository::deleteById);
    }

    private Wallet save(final Wallet aWallet) {
        final WalletJpaEntity aWalletJpaEntity = walletRepository.save(WalletJpaEntity.from(aWallet));
        return aWalletJpaEntity.toAggregate();
    }
}
