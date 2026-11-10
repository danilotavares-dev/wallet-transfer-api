package io.github.danilotavares.financeapi.application.usecase.user;

import io.github.danilotavares.financeapi.application.gateway.UserGateway;
import io.github.danilotavares.financeapi.application.gateway.WalletGateway;
import io.github.danilotavares.financeapi.domain.entity.User;
import io.github.danilotavares.financeapi.domain.entity.Wallet;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Transactional
public class CreateUserUseCase {
    private final UserGateway userGateway;
    private final WalletGateway walletGateway;

    public CreateUserUseCase(UserGateway userGateway, WalletGateway walletGateway) {
        this.userGateway = userGateway;
        this.walletGateway = walletGateway;
    }

    public User execute(String full_name, String cpf, String email) {
        User newUser = new User(UUID.randomUUID(), full_name, cpf, email);
        User savedUser =  userGateway.save(newUser);

        Wallet newWallet = new Wallet(
                UUID.randomUUID(),
                savedUser,
                new BigDecimal("1000.00"), // Mil reais de saldo inicial...
                null
        );
        walletGateway.save(newWallet);

        return savedUser;
    }
}
