package io.github.danilotavares.financeapi.infrastructure.controller.exception;

import java.time.LocalDateTime;

public record StandardError(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {}