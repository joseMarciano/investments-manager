package com.investment.managment.execution.presenters;

import com.investment.managment.api.execution.models.CreateExecutionResponse;
import com.investment.managment.api.execution.models.UpdateExecutionResponse;
import com.investment.managment.execution.create.CreateExecutionCommandOutput;
import com.investment.managment.execution.update.UpdateExecutionCommandOutput;

public interface ExecutionAPIPresenter {

    static UpdateExecutionResponse present(final UpdateExecutionCommandOutput output) {
        return new UpdateExecutionResponse(
                output.id().getValue(),
                output.stockId().getValue(),
                output.walletId().getValue(),
                output.profitPercentage(),
                output.buyExecutedQuantity(),
                output.buyExecutedPrice(),
                output.buyExecutedVolume(),
                output.sellExecutedQuantity(),
                output.sellExecutedPrice(),
                output.sellExecutedVolume(),
                output.status(),
                output.boughtAt(),
                output.soldAt(),
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
                output.buyExecutedQuantity(),
                output.buyExecutedPrice(),
                output.buyExecutedVolume(),
                output.status(),
                output.boughtAt(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}
