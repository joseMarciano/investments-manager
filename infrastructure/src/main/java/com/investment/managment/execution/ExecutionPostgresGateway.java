package com.investment.managment.execution;

import com.investment.managment.Identifier;
import com.investment.managment.execution.page.ExecutionSearchQuery;
import com.investment.managment.execution.persistence.ExecutionJpaEntity;
import com.investment.managment.execution.persistence.ExecutionRepository;
import com.investment.managment.execution.summary.ExecutionSummaryByStock;
import com.investment.managment.page.Pagination;
import com.investment.managment.util.PaginationUtil;
import com.investment.managment.util.SpecificationUtil;
import com.investment.managment.wallet.WalletID;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class ExecutionPostgresGateway implements ExecutionGateway {

    private final ExecutionRepository executionRepository;

    public ExecutionPostgresGateway(final ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }

    @Override
    public Execution create(final Execution anExecution) {
        return save(anExecution);
    }

    @Override
    public Execution update(final Execution anExecution) {
        return save(anExecution);
    }

    @Override
    public Optional<Execution> findById(final ExecutionID anId) {
        return this.executionRepository.findById(anId.getValue()).map(ExecutionJpaEntity::toAggregate);
    }

    @Override
    public List<Execution> findAllByOriginId(final ExecutionID origin) {
        return this.executionRepository.findByOrigin_Id(origin.getValue()).stream().map(ExecutionJpaEntity::toAggregate).toList();
    }

    @Override
    public boolean existsByOriginId(final ExecutionID... originIds) {
        final var ids =
                ofNullable(originIds)
                        .map(it -> Arrays.stream(it).map(ExecutionID::getValue).collect(Collectors.toSet()))
                        .orElse(Collections.emptySet());
        return this.executionRepository.existsByOrigin_IdIn(ids);
    }

    @Override
    public List<ExecutionSummaryByStock> getExecutionSummaryByStock(final WalletID aWalletID) {
        return this.executionRepository.getExecutionSummaryByStock(aWalletID.getValue());
    }

    @Override
    public Pagination<Execution> findAll(final ExecutionSearchQuery query) {
        final var searchQuery = query.searchQuery();

        Specification<ExecutionJpaEntity> specification = null;
        final var walletSpecification = buildForeignKeyFilter(query.walletID(), "wallet.id");
        final var stockSpecification = buildForeignKeyFilter(query.stockID(), "stock.id");

        specification = walletSpecification;
        specification = Objects.nonNull(specification)
                ? specification.and(stockSpecification)
                : stockSpecification;

        Page<ExecutionJpaEntity> page = this.executionRepository.findAll(
                specification,
                PaginationUtil.buildPage(searchQuery)
        );

        return new Pagination<>(
                searchQuery.offset(),
                searchQuery.limit(),
                page.getTotalElements(),
                page.getContent()
        ).map(ExecutionJpaEntity::toAggregate);
    }

    private static Specification<ExecutionJpaEntity> buildForeignKeyFilter(final Identifier<String> aForeignKey, final String propName) {
        return ofNullable(aForeignKey)
                .map(Identifier::getValue)
                .map(identifier -> SpecificationUtil.<ExecutionJpaEntity>equal(propName, identifier))
                .orElse(null);
    }

    @Override
    public void deleteById(final ExecutionID anId) {
        ofNullable(anId)
                .map(Identifier::getValue)
                .ifPresent(executionRepository::deleteById);
    }

    private Execution save(final Execution anExecution) {
        return this.executionRepository.save(ExecutionJpaEntity.from(anExecution)).toAggregate();
    }
}
