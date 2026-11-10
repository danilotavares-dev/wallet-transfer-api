package io.github.danilotavares.financeapi.application.usecase.user;

import io.github.danilotavares.financeapi.application.gateway.UserGateway;
import io.github.danilotavares.financeapi.domain.entity.User;

import java.util.UUID;

public class UserUseCase {
    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User execute(String full_name, String cpf, String email) {
        User newUser = new User(UUID.randomUUID(), full_name, cpf, email);

        return userGateway.save(newUser);
    }
}
