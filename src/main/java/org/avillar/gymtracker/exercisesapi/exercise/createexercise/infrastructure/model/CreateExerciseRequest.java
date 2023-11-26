package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record CreateExerciseRequest(
    @NotBlank(message = "The name is required")
        @Size(min = 10, max = 100, message = "The name characters must be between 10 and 255")
        @Schema(description = "Exercise name", example = "bench press")
        String name,
    @Size(min = 10, max = 255, message = "The description characters must be between 10 and 255")
        @Schema(description = "Exercise description", example = "barbell flat press")
        String description,
    @NotNull(message = "Unilateral is required")
        @Schema(description = "Exercise is executed unilaterally", example = "false")
        Boolean unilateral,
    @NotNull(message = "LoadTypeId is required")
        @Schema(
            description = "LoadType identifier",
            example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
        UUID loadTypeId,
    @Size(max = 5, message = "The muscleSubGroups must be less than 5")
        @Schema(
            description = "MuscleSubGroups identifiers",
            example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
        List<UUID> muscleSubGroupsIds,
    @Size(max = 10, message = "The muscleGroups must be less than 10")
        @Schema(description = "MuscleGroups identifiers with theirs weight coefficients")
        List<MuscleGroupExercises> muscleGroups) {

  public record MuscleGroupExercises(
      @NotNull(message = "MuscleGroupId is required")
          @Schema(
              description = "MuscleGroup identifier",
              example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
          UUID muscleGroupId,
      @NotNull(message = "Weight is required")
          @DecimalMin(value = "0.1", message = "The coefficient must be greater than 0.1")
          @DecimalMax(value = "1", message = "The coefficient must be less than 1")
          @Schema(description = "MuscleGroup coefficient weight", example = "0.7")
          Double weight) {}
}
