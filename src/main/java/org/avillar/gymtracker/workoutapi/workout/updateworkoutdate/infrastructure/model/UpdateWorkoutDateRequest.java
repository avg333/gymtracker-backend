package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UpdateWorkoutDateRequest(
    @NotNull(message = "The date is obligatory") LocalDate date) {}
