package io.github.danilotavares.financeapi.application.gateway;

import io.github.danilotavares.financeapi.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    Optional<User> findById(UUID id);
    List<User> findAll();
    User save(User user);
}
