package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record CreateSetGroupRequest(
    @NotNull(message = "The 'exerciseId' field is required") UUID exerciseId,
    @Size(max = 255, message = "Description should be between 0 and 255 characters")
        String description) {}
