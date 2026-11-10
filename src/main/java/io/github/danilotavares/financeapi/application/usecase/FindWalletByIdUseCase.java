package io.github.danilotavares.financeapi.application.usecase;

import io.github.danilotavares.financeapi.application.gateway.WalletGateway;
import io.github.danilotavares.financeapi.domain.entity.Wallet;

import java.util.UUID;

public class FindWalletByIdUseCase {
    private final WalletGateway walletGateway;

    public FindWalletByIdUseCase(WalletGateway walletGateway) {
        this.walletGateway = walletGateway;
    }

    public Wallet execute(UUID id) {
        return walletGateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada."));
    }
}
