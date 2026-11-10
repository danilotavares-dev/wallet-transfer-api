package io.github.danilotavares.financeapi.infrastructure.controller;

import io.github.danilotavares.financeapi.application.usecase.TransferUseCase;
import io.github.danilotavares.financeapi.domain.entity.Transaction;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.TransferRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransferUseCase transferUseCase;

    public TransactionController(TransferUseCase transferUseCase) {
        this.transferUseCase = transferUseCase;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequestDto request) {
        Transaction transaction = transferUseCase.execute(
                request.senderId(),
                request.receiverId(),
                request.amount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }
}
