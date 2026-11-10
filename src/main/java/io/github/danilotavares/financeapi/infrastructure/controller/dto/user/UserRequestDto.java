package io.github.danilotavares.financeapi.infrastructure.controller.dto.user;

public record UserRequestDto(
        String fullName,
        String cpf,
        String email
) {}
