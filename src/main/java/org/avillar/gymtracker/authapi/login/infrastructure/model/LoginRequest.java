package org.avillar.gymtracker.authapi.login.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "The username is required")
        @Schema(description = "Username of the user", example = "avillar")
        String username,
    @NotBlank(message = "The password is required")
        @Schema(description = "Password of the user", example = "avillarpass")
        String password) {}
