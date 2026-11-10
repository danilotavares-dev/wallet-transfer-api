package io.github.danilotavares.financeapi.domain.entity;

import io.github.danilotavares.financeapi.exceptions.InsufficientBalanceException;

import java.math.BigDecimal;
import java.util.UUID;

public class Wallet {
    private final UUID id;
    private final User user;
    private BigDecimal balance;
    private final Long version;

    public Wallet(UUID id, User user, BigDecimal balance, Long version) {
        this.id = id;
        this.user = user;
        this.balance = balance;
        this.version = version;
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0){
            throw new InsufficientBalanceException("ERRO: Saldo insuficiente");
        }
        return true;
    }

    public void debit(BigDecimal amount) {
        hasSufficientBalance(amount);
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Long getVersion() {
        return version;
    }
}
