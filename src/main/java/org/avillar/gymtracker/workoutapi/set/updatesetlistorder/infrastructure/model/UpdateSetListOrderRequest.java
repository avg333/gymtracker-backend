package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateSetListOrderRequest(
    @NotNull(message = "The list order must not be null")
        @PositiveOrZero(message = "The list order must be a non-negative number")
        Integer listOrder) {}
