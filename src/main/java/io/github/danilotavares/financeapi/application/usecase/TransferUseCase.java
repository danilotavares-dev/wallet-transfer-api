package io.github.danilotavares.financeapi.application.usecase;

import io.github.danilotavares.financeapi.application.gateway.TransactionGateway;
import io.github.danilotavares.financeapi.application.gateway.WalletGateway;
import io.github.danilotavares.financeapi.domain.entity.Transaction;
import io.github.danilotavares.financeapi.domain.entity.Wallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransferUseCase {
    private final WalletGateway walletGateway;
    private final TransactionGateway transactionGateway;

    public TransferUseCase(TransactionGateway transactionGateway, WalletGateway walletGateway) {
        this.transactionGateway = transactionGateway;
        this.walletGateway = walletGateway;
    }

    public Transaction execute(UUID senderId, UUID receiverId, BigDecimal amount) {
        Wallet sender = walletGateway.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Carteira de origem não encontrada."));

        Wallet receiver = walletGateway.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Carteira de destino não encontrada."));

        sender.debit(amount);
        receiver.credit(amount);

        Transaction transaction = new Transaction(
                UUID.randomUUID(),
                sender,
                receiver,
                amount,
                LocalDateTime.now()
        );

        walletGateway.save(sender);
        walletGateway.save(receiver);

        return transactionGateway.save(transaction);
    }
}
