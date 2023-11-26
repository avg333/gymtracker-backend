package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSetRequest(
    @Schema(description = "The description of the set", maxLength = 255)
        @Size(
            max = 255,
            message = "The description must have a length between 0 and 255 characters")
        String description,
    @Schema(
            description = "The number of repetitions for the set",
            requiredMode = RequiredMode.REQUIRED,
            minimum = "0",
            maximum = "100")
        @NotNull(message = "The number of reps must not be null")
        @Min(value = 0, message = "The number of reps must be between 0 and 100")
        @Max(value = 100, message = "The number of reps must be between 0 and 100")
        Integer reps,
    @Schema(
            description = "The Rate of Perceived Exertion for the set",
            requiredMode = RequiredMode.REQUIRED,
            minimum = "0",
            maximum = "10")
        @NotNull(message = "The RIR must not be null")
        @Min(value = 0, message = "The RIR must be between 0 and 10")
        @Max(value = 10, message = "The RIR must be between 0 and 10")
        Double rir,
    @Schema(
            description = "The weight used for the set",
            requiredMode = RequiredMode.REQUIRED,
            minimum = "0",
            maximum = "1000")
        @NotNull(message = "The weight must not be null")
        @Min(value = 0, message = "The weight must be between 0 and 1000")
        @Max(value = 1000, message = "The weight must be between 0 and 1000")
        Double weight,
    @Schema(
            description = "Indicates whether the set is completed or not",
            requiredMode = RequiredMode.REQUIRED)
        @NotNull(message = "The 'completed' field must not be null")
        Boolean completed) {}
