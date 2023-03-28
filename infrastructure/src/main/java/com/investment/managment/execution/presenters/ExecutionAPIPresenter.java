package com.investment.managment.execution.presenters;

import com.investment.managment.Identifier;
import com.investment.managment.execution.create.CreateExecutionCommandOutput;
import com.investment.managment.execution.findById.FindExecutionByIdCommandOutput;
import com.investment.managment.execution.models.*;
import com.investment.managment.execution.page.PageExecutionCommandOutput;
import com.investment.managment.execution.sell.SellExecutionCommandOutput;
import com.investment.managment.execution.summarybystock.SummaryExecutionCommandOutput;
import com.investment.managment.execution.totalizator.ExecutionsTotalizatorCommandOuput;
import com.investment.managment.execution.update.UpdateExecutionCommandOutput;

import java.util.Optional;

public interface ExecutionAPIPresenter {

    static UpdateExecutionResponse present(final UpdateExecutionCommandOutput output) {
        return new UpdateExecutionResponse(
                output.id().getValue(),
                output.stockId().getValue(),
                output.walletId().getValue(),
                output.profitPercentage(),
                output.executedQuantity(),
                output.executedPrice(),
                output.executedVolume(),
                output.status(),
                output.executedAt(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static CreateExecutionResponse present(final CreateExecutionCommandOutput output) {
        return new CreateExecutionResponse(
                output.id().getValue(),
                output.stockId().getValue(),
                output.walletId().getValue(),
                output.profitPercentage(),
                output.executedQuantity(),
                output.executedPrice(),
                output.executedVolume(),
                output.status(),
                output.executedAt(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static FindByIdExecutionResponse present(final FindExecutionByIdCommandOutput output) {
        return new FindByIdExecutionResponse(
                output.id().getValue(),
                getIdentifierValue(output.origin()),
                output.stockId().getValue(),
                output.walletId().getValue(),
                output.profitPercentage(),
                output.executedQuantity(),
                output.executedPrice(),
                output.executedVolume(),
                output.status(),
                output.executedAt(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static SummaryExecutionByStockResponse present(final SummaryExecutionCommandOutput output) {
        return new SummaryExecutionByStockResponse(
                output.stockId(),
                output.symbol(),
                output.totalQuantity(),
                output.totalSoldQuantity(),
                output.totalCustodyQuantity()
        );
    }

    static SellExecutionResponse present(final SellExecutionCommandOutput output) {
        return new SellExecutionResponse(
                output.id().getValue(),
                getIdentifierValue(output.originId()),
                output.stockId().getValue(),
                output.walletId().getValue(),
                output.profitPercentage(),
                output.executedQuantity(),
                output.executedPrice(),
                output.executedVolume(),
                output.status(),
                output.executedAt(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static PageExecutionResponse present(final PageExecutionCommandOutput output) {
        return new PageExecutionResponse(
                output.id().getValue(),
                getIdentifierValue(output.origin()),
                output.stockId().getValue(),
                output.walletId().getValue(),
                output.profitPercentage(),
                output.executedQuantity(),
                output.executedPrice(),
                output.executedVolume(),
                output.status(),
                output.pnlOpen(),
                output.pnlClose(),
                output.executedAt(),
                output.createdAt(),
                output.updatedAt()
        );
    }

    static ExecutionsTotalizatorResponse present(final ExecutionsTotalizatorCommandOuput output) {
        return new ExecutionsTotalizatorResponse(
                output.totalSoldQuantity(),
                output.totalBoughtQuantity(),
                output.totalPnlOpen(),
                output.totalPnlClose()
        );
    }

    private static <T> T getIdentifierValue(final Identifier<T> identifier) {
        return Optional.ofNullable(identifier).map(Identifier::getValue).orElse(null);
    }
}
