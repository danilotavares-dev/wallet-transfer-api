package io.github.danilotavares.financeapi.infrastructure.controller;

import io.github.danilotavares.financeapi.application.usecase.user.FindAllUsersUseCase;
import io.github.danilotavares.financeapi.application.usecase.user.FindUserByIdUseCase;
import io.github.danilotavares.financeapi.application.usecase.user.CreateUserUseCase;
import io.github.danilotavares.financeapi.domain.entity.User;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.user.UserRequestDto;
import io.github.danilotavares.financeapi.infrastructure.controller.dto.user.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final CreateUserUseCase userUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final FindAllUsersUseCase findAllUsersUseCase;

    public UserController(CreateUserUseCase userUseCase, FindUserByIdUseCase findUserByIdUseCase, FindAllUsersUseCase findAllUsersUseCase) {
        this.userUseCase = userUseCase;
        this.findUserByIdUseCase = findUserByIdUseCase;
        this.findAllUsersUseCase = findAllUsersUseCase;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserRequestDto request) {
        userUseCase.execute(
                request.fullName(),
                request.cpf(),
                request.email());
    }

    @GetMapping
    public List<UserResponseDto> findAll() {
        List<User> users =  findAllUsersUseCase.execute();

        return users.stream().map(user -> new UserResponseDto(
                user.getId(),
                user.getFull_name(),
                user.getCpf(),
                user.getEmail()
        )).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id) {
        User user = findUserByIdUseCase.execute(id);

        UserResponseDto response = new UserResponseDto(
                user.getId(),
                user.getFull_name(),
                user.getCpf(),
                user.getEmail()
        );

        return ResponseEntity.ok(response);
    }
}
