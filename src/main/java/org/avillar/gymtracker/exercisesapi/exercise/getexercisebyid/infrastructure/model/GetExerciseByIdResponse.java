package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

public record GetExerciseByIdResponse(
    @Schema(description = "Exercise id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560") UUID id,
    @Schema(description = "Exercise name", example = "bench press") String name,
    @Schema(description = "Exercise description", example = "barbell flat press")
        String description,
    @Schema(description = "Exercise is executed unilaterally", example = "false")
        boolean unilateral,
    LoadType loadType,
    List<MuscleSubGroup> muscleSubGroups,
    List<MuscleGroup> muscleGroups) {

  public record LoadType(
      @Schema(description = "LoadType id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
          UUID id,
      @Schema(description = "LoadType name", example = "bar") String name,
      @Schema(
              description = "LoadType description",
              example = "Weight guide machine used for weight training")
          String description) {}

  public record MuscleSubGroup(
      @Schema(description = "MuscleSubGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
          UUID id,
      @Schema(description = "MuscleSubGroup name", example = "Lower chest") String name,
      @Schema(description = "MuscleSubGroup name", example = "Lower part of the pectoral")
          String description) {}

  public record MuscleGroup(
      @Schema(description = "MuscleGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
          UUID id,
      @Schema(description = "Exercise name", example = "bench press") String name,
      @Schema(description = "Exercise description", example = "barbell flat press")
          String description,
      @Schema(description = "MuscleGroup coefficient weight", example = "0.7") Double weight) {}
}
