package io.github.danilotavares.financeapi.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponseDto(
        UUID id,
        BigDecimal amount,
        LocalDateTime createdAt
) {}
