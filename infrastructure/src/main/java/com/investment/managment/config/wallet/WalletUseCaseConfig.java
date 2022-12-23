package com.investment.managment.config.wallet;

import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.create.CreateWalletUseCase;
import com.investment.managment.wallet.deleteById.DeleteWalletByIdUseCase;
import com.investment.managment.wallet.findById.FindWalletByIdUseCase;
import com.investment.managment.wallet.update.UpdateWalletUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletUseCaseConfig {
    @Bean
    public CreateWalletUseCase createWalletUseCase(final WalletGateway walletGateway) {
        return new CreateWalletUseCase(walletGateway);
    }

    @Bean
    public UpdateWalletUseCase updateWalletUseCase(final WalletGateway walletGateway) {
        return new UpdateWalletUseCase(walletGateway);
    }

    @Bean
    public DeleteWalletByIdUseCase deleteWalletByIdUseCase(final WalletGateway walletGateway) {
        return new DeleteWalletByIdUseCase(walletGateway);
    }

    @Bean
    public FindWalletByIdUseCase findWalletByIdUseCase(final WalletGateway walletGateway) {
        return new FindWalletByIdUseCase(walletGateway);
    }
}
