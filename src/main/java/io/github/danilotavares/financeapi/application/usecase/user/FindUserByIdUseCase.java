package io.github.danilotavares.financeapi.application.usecase.user;

import io.github.danilotavares.financeapi.application.gateway.UserGateway;
import io.github.danilotavares.financeapi.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public class FindUserByIdUseCase {
    private UserGateway userGateway;

    public FindUserByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(UUID id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado para o ID: " + id));
    }
}
