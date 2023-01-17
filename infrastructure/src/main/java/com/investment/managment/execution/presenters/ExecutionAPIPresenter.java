package com.investment.managment.execution.presenters;

import com.investment.managment.Identifier;
import com.investment.managment.api.execution.models.CreateExecutionResponse;
import com.investment.managment.api.execution.models.FindByIdExecutionResponse;
import com.investment.managment.api.execution.models.SummaryExecutionByStockResponse;
import com.investment.managment.api.execution.models.UpdateExecutionResponse;
import com.investment.managment.execution.create.CreateExecutionCommandOutput;
import com.investment.managment.execution.findById.FindExecutionByIdCommandOutput;
import com.investment.managment.execution.summarybystock.SummaryExecutionCommandOutput;
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
                Optional.ofNullable(output.origin()).map(Identifier::getValue).orElse(null),
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

    static SummaryExecutionByStockResponse present(final SummaryExecutionCommandOutput response) {
        return new SummaryExecutionByStockResponse(
                response.symbol()
        );
    }
}
