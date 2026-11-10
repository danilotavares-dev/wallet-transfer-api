package io.github.danilotavares.financeapi.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.danilotavares.financeapi.exceptions.InsufficientBalanceException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallets")
public class WalletEntity {
    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, unique = true)
    private UserEntity user;

    @JsonIgnore
    @Column(nullable = false)
    private BigDecimal balance;

    @Version
    private Long version;

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
        this.balance.add(amount);
    }
}
