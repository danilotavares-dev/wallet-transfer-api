package io.github.danilotavares.financeapi.infrastructure.infraestructure.config;

import io.github.danilotavares.financeapi.application.gateway.TransactionGateway;
import io.github.danilotavares.financeapi.application.gateway.UserGateway;
import io.github.danilotavares.financeapi.application.gateway.WalletGateway;
import io.github.danilotavares.financeapi.application.usecase.TransferUseCase;
import io.github.danilotavares.financeapi.application.usecase.user.FindAllUsersUseCase;
import io.github.danilotavares.financeapi.application.usecase.user.FindUserByIdUseCase;
import io.github.danilotavares.financeapi.application.usecase.user.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public TransferUseCase transferUseCase(WalletGateway walletGateway, TransactionGateway transactionGateway) {
        return new TransferUseCase(transactionGateway, walletGateway);
    }

    @Bean
    public UserUseCase userUseCase(UserGateway userGateway) {
        return new UserUseCase(userGateway);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserGateway userGateway) {
        return new FindUserByIdUseCase(userGateway);
    }

    @Bean
    public FindAllUsersUseCase findAllUsersUseCase(UserGateway userGateway) {
        return new FindAllUsersUseCase(userGateway);
    }
}
