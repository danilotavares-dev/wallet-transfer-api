package io.github.danilotavares.financeapi.application.gateway;

import io.github.danilotavares.financeapi.domain.entity.Wallet;

import java.util.Optional;
import java.util.UUID;

public interface WalletGateway {
    Optional<Wallet> findById(UUID id);
    Wallet save(Wallet wallet);
}
