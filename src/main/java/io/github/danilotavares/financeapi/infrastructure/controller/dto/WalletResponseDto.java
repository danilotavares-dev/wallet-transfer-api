package io.github.danilotavares.financeapi.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponseDto(
        UUID id,
        UUID userId,
        BigDecimal balance
) {}
