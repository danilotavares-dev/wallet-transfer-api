package io.github.danilotavares.financeapi.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Transactions")
public class TransactionEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sender_wallet")
    private WalletEntity senderWallet;

    @ManyToOne
    @JoinColumn(name = "receiver_wallet")
    private WalletEntity receiverWallet;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
