package io.github.danilotavares.financeapi.application.usecase;

import io.github.danilotavares.financeapi.application.gateway.TransactionGateway;
import io.github.danilotavares.financeapi.domain.entity.Transaction;

import java.util.List;
import java.util.UUID;

public class GetWalletStatementUseCase {
    private final TransactionGateway transactionGateway;

    public GetWalletStatementUseCase(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    public List<Transaction> execute(UUID id) {
        return transactionGateway.findAllByWallet(id);
    }
}
