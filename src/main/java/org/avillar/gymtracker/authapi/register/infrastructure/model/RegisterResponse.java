package org.avillar.gymtracker.authapi.register.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record RegisterResponse(
    @Schema(description = "JWT Type", example = "Bearer") String type,
    @Schema(
            description = "User JWT token",
            example =
                "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIyNzA5NjlhYi0yOGQ0LTRkMTktOGVhOC1kMmQ0YjA5MWM4ZDciLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiVVNFUl9ST0xFIn1dLCJzdWIiOiJjaGVtYSIsImp0aSI6IjI3MDk2OWFiLTI4ZDQtNGQxOS04ZWE4LWQyZDRiMDkxYzhkNyIsImlhdCI6MTY5MDQ5MjA0NCwiZXhwIjoxNjkwNTc4NDQ0fQ.0ZDErsUFyjg6mxrhNDt2nejnlKZ_5cyMurtNTODLgQw")
        String token,
    @Schema(description = "User ID", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560") UUID id,
    @Schema(description = "User username", example = "avillar") String username) {}
