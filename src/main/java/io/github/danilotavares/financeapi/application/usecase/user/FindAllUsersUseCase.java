package io.github.danilotavares.financeapi.application.usecase.user;

import io.github.danilotavares.financeapi.application.gateway.UserGateway;
import io.github.danilotavares.financeapi.domain.entity.User;

import java.util.List;

public class FindAllUsersUseCase {
    private final UserGateway userGateway;

    public FindAllUsersUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<User> execute() {
        return userGateway.findAll();
    }
}
