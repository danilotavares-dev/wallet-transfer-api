package io.github.danilotavares.financeapi.infrastructure.controller;

import io.github.danilotavares.financeapi.application.usecase.FindWalletByIdUseCase;
import io.github.danilotavares.financeapi.domain.entity.Wallet;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.WalletResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
    private final FindWalletByIdUseCase findWalletByIdUseCase;

    public WalletController(FindWalletByIdUseCase findWalletByIdUseCase) {
        this.findWalletByIdUseCase = findWalletByIdUseCase;
    }

    @RequestMapping("/{id}")
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
}
