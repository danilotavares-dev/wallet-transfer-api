package io.github.danilotavares.financeapi.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final Wallet senderWallet;
    private final Wallet receiverWallet;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;

    public Transaction(UUID id, Wallet senderWallet, Wallet receiverWallet, BigDecimal amount, LocalDateTime createdAt) {
        validateTransaction(senderWallet, receiverWallet, amount);

        this.id = id;
        this.senderWallet = senderWallet;
        this.receiverWallet = receiverWallet;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    private void validateTransaction(Wallet sender, Wallet receiver, BigDecimal transferAmount) {
        if (transferAmount == null || transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("ERRO: O valor da transação deve ser maior que zero.");
        }

        if (sender != null && receiver != null && sender.getId().equals(receiver.getId())) {
            throw new IllegalArgumentException("ERRO: Não é possível realizar uma transferência para a mesma conta.");
        }
    }

    public UUID getId() {
        return id;
    }

    public Wallet getSenderWallet() {
        return senderWallet;
    }

    public Wallet getReceiverWallet() {
        return receiverWallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
