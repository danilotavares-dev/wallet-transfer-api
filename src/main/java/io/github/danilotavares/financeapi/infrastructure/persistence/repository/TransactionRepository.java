package io.github.danilotavares.financeapi.infrastructure.persistence.repository;

import io.github.danilotavares.financeapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.senderWallet.id = :walletId OR t.receiverWallet.id = :walletId ORDER BY t.createdAt DESC")
    List<TransactionEntity> findAllByWalletId(@Param("walletId") UUID walletId);
}
