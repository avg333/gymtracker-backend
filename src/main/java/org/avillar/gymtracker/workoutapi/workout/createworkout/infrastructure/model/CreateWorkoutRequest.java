package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateWorkoutRequest(
    @NotNull(message = "The date is obligatory") LocalDate date,
    @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
        String description) {}
