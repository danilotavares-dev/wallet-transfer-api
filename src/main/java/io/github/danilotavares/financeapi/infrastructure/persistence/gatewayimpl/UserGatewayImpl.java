package io.github.danilotavares.financeapi.infrastructure.persistence.gatewayimpl;

import io.github.danilotavares.financeapi.application.gateway.UserGateway;
import io.github.danilotavares.financeapi.domain.entity.User;
import io.github.danilotavares.financeapi.infrastructure.persistence.entity.UserEntity;
import io.github.danilotavares.financeapi.infrastructure.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserGatewayImpl implements UserGateway {
    private UserRepository userRepository;

    public UserGatewayImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public User save(User userDomain) {
        UserEntity entityToSave = toEntity(userDomain);
        UserEntity savedEntity = userRepository.save(entityToSave);

        return toDomain(savedEntity);
    }

    private User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getFull_name(),
                entity.getCpf(),
                entity.getEmail()
        );
    }

    private UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setFull_name(domain.getFull_name());
        entity.setCpf(domain.getCpf());
        entity.setEmail(domain.getEmail());
        return entity;
    }
}
