package io.github.danilotavares.financeapi.infrastructure.controller.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
