package io.github.danilotavares.financeapi.application.gateway;

import io.github.danilotavares.financeapi.domain.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionGateway {
    Optional<Transaction> findById(UUID id);
    List<Transaction> findAllByWallet(UUID Id);
    Transaction save(Transaction transaction);
}
