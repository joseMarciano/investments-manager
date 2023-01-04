package com.investment.managment.stock;

import com.investment.managment.page.Pagination;
import com.investment.managment.page.SearchQuery;
import com.investment.managment.stock.persistence.StockJpaEntity;
import com.investment.managment.stock.persistence.StockRepository;
import com.investment.managment.util.PaginationUtil;
import com.investment.managment.util.SpecificationUtil;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Set.of;

@Component
public class StockGatewayImpl implements StockGateway {

    private final StockRepository stockRepository;

    public StockGatewayImpl(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock create(final Stock aStock) {
        return save(aStock);
    }


    @Override
    public Stock update(final Stock aStock) {
        return save(aStock);
    }

    @Override
    public Optional<Stock> findById(final StockID anId) {
        return stockRepository.findById(anId.getValue()).map(StockJpaEntity::toAggregate);
    }
    @Override
    public Optional<Stock> findBySymbol(final String aSymbol) {
        return stockRepository.findBySymbol(aSymbol).map(StockJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Stock> findAll(final SearchQuery query) {
        final var walletSpecification = Optional.ofNullable(query.filter())
                .map(filter -> SpecificationUtil.<StockJpaEntity>like(
                        of("symbol"), filter
                )).orElse(null);

        Page<StockJpaEntity> page = stockRepository.findAll(
                walletSpecification,
                PaginationUtil.buildPage(query)
        );
        return new Pagination<>(
                query.offset(),
                query.limit(),
                page.getTotalElements(),
                page.getContent()
        ).map(StockJpaEntity::toAggregate);
    }

    @Override
    public void deleteById(final StockID anId) {
        this.findById(anId)
                .map(Stock::getId)
                .map(StockID::getValue)
                .ifPresent(stockRepository::deleteById);
    }

    private Stock save(final Stock aStock) {
        final StockJpaEntity aStockJpaEntity = stockRepository.save(StockJpaEntity.from(aStock));
        return aStockJpaEntity.toAggregate();
    }
}
