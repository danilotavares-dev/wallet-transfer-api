package io.github.danilotavares.financeapi.domain.entity;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String full_name;
    private final String cpf;
    private final String email;

    public User(UUID id, String full_name, String cpf, String email) {
        this.id = id;
        this.full_name = full_name;
        this.cpf = cpf;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }
}
