package io.github.danilotavares.financeapi.infrastructure.persistence.gatewayimpl;

import io.github.danilotavares.financeapi.application.gateway.TransactionGateway;
import io.github.danilotavares.financeapi.domain.entity.Transaction;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.TransactionEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.WalletEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.TransactionRepository;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.WalletRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TransactionGatewayImpl implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    public TransactionGatewayImpl(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return transactionRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = toEntity(transaction);
        TransactionEntity savedEntity = transactionRepository.save(entity);
        return toDomain(savedEntity);
    }

    private TransactionEntity toEntity(Transaction domain) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(domain.getId());
        entity.setAmount(domain.getAmount());
        entity.setCreatedAt(domain.getCreatedAt());

        if (domain.getSenderWallet() != null) {
            entity.setSenderWallet(walletRepository.getReferenceById(domain.getSenderWallet().getId()));
        }

        if (domain.getReceiverWallet() != null) {
            entity.setReceiverWallet(walletRepository.getReferenceById(domain.getReceiverWallet().getId()));
        }

        return entity;
    }

    private Transaction toDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                null,
                null,
                entity.getAmount(),
                entity.getCreatedAt()
        );
    }
}