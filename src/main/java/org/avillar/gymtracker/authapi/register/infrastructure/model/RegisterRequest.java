package org.avillar.gymtracker.authapi.register.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "The username is required")
        @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
        @Schema(description = "Username of the user", example = "avillar")
        String username,
    @NotBlank(message = "The password is required")
        @Size(min = 5, max = 50, message = "Password must be between 5 and 50 characters")
        @Schema(description = "Password of the user", example = "avillarpass")
        String password,
    @Schema(description = "Register code", example = "FREE2024") String registerCode) {}
