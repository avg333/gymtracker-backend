package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record GetExercisesByFilterResponse(
    @Schema(description = "Exercise id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560") UUID id,
    @Schema(description = "Exercise name", example = "bench press") String name,
    @Schema(description = "Exercise description", example = "barbell flat press")
        String description,
    @Schema(description = "Exercise is executed unilaterally", example = "false")
        boolean unilateral) {}
