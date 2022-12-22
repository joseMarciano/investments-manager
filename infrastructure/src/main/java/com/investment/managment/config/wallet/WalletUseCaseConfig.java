package com.investment.managment.config.wallet;

import com.investment.managment.wallet.WalletGateway;
import com.investment.managment.wallet.create.CreateWalletUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WalletUseCaseConfig {
    @Bean
    public CreateWalletUseCase createWalletUseCase(final WalletGateway walletGateway) {
        return new CreateWalletUseCase(walletGateway);
    }
}
