package io.github.danilotavares.financeapi.infrastructure.persistence.repository;

import io.github.danilotavares.financeapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
}
