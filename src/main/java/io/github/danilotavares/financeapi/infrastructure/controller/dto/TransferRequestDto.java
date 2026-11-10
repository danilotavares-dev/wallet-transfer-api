package io.github.danilotavares.financeapi.infrastructure.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(
        UUID senderId,
        UUID receiverId,
        BigDecimal amount
) {}
