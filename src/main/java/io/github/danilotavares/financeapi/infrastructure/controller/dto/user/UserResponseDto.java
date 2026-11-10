package io.github.danilotavares.financeapi.infrastructure.controller.dto.user;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String fullName,
        String cpf,
        String email
) {}
