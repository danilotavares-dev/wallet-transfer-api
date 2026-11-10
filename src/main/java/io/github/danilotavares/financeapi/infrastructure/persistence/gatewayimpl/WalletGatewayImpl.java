package io.github.danilotavares.financeapi.infrastructure.persistence.gatewayimpl;

import io.github.danilotavares.financeapi.application.gateway.WalletGateway;
import io.github.danilotavares.financeapi.domain.entity.User;
import io.github.danilotavares.financeapi.domain.entity.Wallet;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.UserEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.WalletEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.UserRepository;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class WalletGatewayImpl implements WalletGateway {
    private final UserRepository userRepository;
    private final WalletRepository springRepository;

    public WalletGatewayImpl(WalletRepository springRepository, UserRepository userRepository) {
        this.springRepository = springRepository;
        this.userRepository = userRepository;
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
        User userDomain = null;

        if (entity.getUser() != null) {
            userDomain = new User(
                    entity.getUser().getId(),
                    entity.getUser().getFull_name(),
                    entity.getUser().getCpf(),
                    entity.getUser().getEmail()
            );
        }

        return new Wallet(
                entity.getId(),
                userDomain,
                entity.getBalance(),
                entity.getVersion()
        );
    }

    private WalletEntity toEntity(Wallet domain) {
        WalletEntity entity = new WalletEntity();
        entity.setId(domain.getId());
        entity.setBalance(domain.getBalance());
        entity.setVersion(domain.getVersion());

        if (domain.getUser() != null) {
            UserEntity userReference = userRepository.getReferenceById(domain.getUser().getId());
            entity.setUser(userReference);
        }
        return entity;
    }
}