package io.github.danilotavares.financeapi.infrastructure.controller;

import io.github.danilotavares.financeapi.application.usecase.FindWalletByIdUseCase;
import io.github.danilotavares.financeapi.application.usecase.GetWalletStatementUseCase;
import io.github.danilotavares.financeapi.domain.entity.Transaction;
import io.github.danilotavares.financeapi.domain.entity.Wallet;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.TransactionResponseDto;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.TransferRequestDto;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.WalletResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final FindWalletByIdUseCase findWalletByIdUseCase;
    private final GetWalletStatementUseCase getWalletStatementUseCase;

    public WalletController(FindWalletByIdUseCase findWalletByIdUseCase, GetWalletStatementUseCase getWalletStatementUseCase) {
        this.findWalletByIdUseCase = findWalletByIdUseCase;
        this.getWalletStatementUseCase = getWalletStatementUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponseDto> findById(@PathVariable UUID id) {
        Wallet wallet = findWalletByIdUseCase.execute(id);

        UUID userId = wallet.getUser() != null ? wallet.getUser().getId() : null;

        WalletResponseDto response = new WalletResponseDto(
                wallet.getId(),
                userId,
                wallet.getBalance()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getStatement(@PathVariable UUID id) {
        List<Transaction> transactions = getWalletStatementUseCase.execute(id);

        List<TransactionResponseDto> response = transactions.stream()
                .map(t -> new TransactionResponseDto(
                        t.getId(),
                        t.getAmount(),
                        t.getCreatedAt()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
