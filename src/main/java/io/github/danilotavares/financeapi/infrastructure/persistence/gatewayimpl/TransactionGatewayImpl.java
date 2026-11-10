package io.github.danilotavares.financeapi.infrastructure.persistence.gatewayimpl;

import io.github.danilotavares.financeapi.application.gateway.TransactionGateway;
import io.github.danilotavares.financeapi.domain.entity.Transaction;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.TransactionEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.WalletEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TransactionGatewayImpl implements TransactionGateway {

    private final TransactionRepository springRepository;

    public TransactionGatewayImpl(TransactionRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Transaction save(Transaction transactionDomain) {
        TransactionEntity entityToSave = toEntity(transactionDomain);

        springRepository.save(entityToSave);

        return transactionDomain;
    }

    private TransactionEntity toEntity(Transaction domain) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(domain.getId());
        entity.setAmount(domain.getAmount());

        WalletEntity sender = new WalletEntity();
        sender.setId(domain.getSenderWallet().getId());

        WalletEntity receiver = new WalletEntity();
        receiver.setId(domain.getReceiverWallet().getId());

        entity.setSenderWallet(sender);
        entity.setReceiverWallet(receiver);

        return entity;
    }
}