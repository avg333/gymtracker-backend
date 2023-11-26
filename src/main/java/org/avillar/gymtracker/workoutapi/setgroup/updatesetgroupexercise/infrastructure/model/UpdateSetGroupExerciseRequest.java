package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateSetGroupExerciseRequest(
    @NotNull(message = "ExerciseId is required") UUID exerciseId) {}
