package io.github.danilotavares.financeapi.infrastructure.persistence.repository;

import io.github.danilotavares.financeapi.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
