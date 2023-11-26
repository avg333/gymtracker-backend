package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

public record GetAllMuscleSupGroupsResponse(
    @Schema(description = "MuscleSupGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
        UUID id,
    @Schema(description = "MuscleSupGroup name", example = "Push") String name,
    @Schema(description = "MuscleSupGroup name", example = "All muscle groups involved in pushing")
        String description,
    List<MuscleGroup> muscleGroups) {

  public record MuscleGroup(
      @Schema(description = "MuscleGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
          UUID id,
      @Schema(description = "MuscleGroup name", example = "Chest") String name,
      @Schema(description = "MuscleGroup name", example = "Principal push muscle")
          String description,
      List<MuscleSubGroup> muscleSubGroups) {

    public record MuscleSubGroup(
        @Schema(description = "MuscleSubGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
            UUID id,
        @Schema(description = "MuscleSubGroup name", example = "Lower chest") String name,
        @Schema(description = "MuscleSubGroup name", example = "Lower part of the pectoral")
            String description) {}
  }
}
