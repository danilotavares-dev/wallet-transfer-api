package io.github.danilotavares.financeapi.infrastructure.persistence.repository;

import io.github.danilotavares.financeapi.infrastructure.persistence.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<WalletEntity, UUID> {
}