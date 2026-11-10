package io.github.danilotavares.financeapi.infrastructure.persistence.gatewayimpl;

import io.github.danilotavares.financeapi.application.gateway.WalletGateway;
import io.github.danilotavares.financeapi.domain.entity.Wallet;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.WalletEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WalletGatewayImpl implements WalletGateway {

    private final WalletRepository springRepository;

    public WalletGatewayImpl(WalletRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return springRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Wallet save(Wallet walletDomain) {
        WalletEntity entityToSave = toEntity(walletDomain);
        WalletEntity savedEntity = springRepository.save(entityToSave);
        return toDomain(savedEntity);
    }

    private Wallet toDomain(WalletEntity entity) {
        return new Wallet(
                entity.getId(),
                null,
                entity.getBalance(),
                entity.getVersion()
        );
    }

    private WalletEntity toEntity(Wallet domain) {
        WalletEntity entity = new WalletEntity();
        entity.setId(domain.getId());
        entity.setBalance(domain.getBalance());
        entity.setVersion(domain.getVersion());
        return entity;
    }
}