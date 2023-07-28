package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSupGroupsResponse {

  @Schema(description = "MuscleSupGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
  private UUID id;

  @Schema(description = "MuscleSupGroup name", example = "Push")
  private String name;

  @Schema(description = "MuscleSupGroup name", example = "All muscles involved in pushing")
  private String description;

  @Schema(description = "List of MuscleSupGroup MuscleGroups")
  private List<MuscleGroup> muscleGroups;

  @Data
  public static class MuscleGroup {

    @Schema(description = "MuscleGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
    private UUID id;

    @Schema(description = "MuscleGroup name", example = "Chest")
    private String name;

    @Schema(description = "MuscleGroup name", example = "Principal push muscle")
    private String description;

    @Schema(description = "List of MuscleGroup MuscleSubGroups")
    private List<MuscleSubGroup> muscleSubGroups;

    @Data
    public static class MuscleSubGroup {

      @Schema(description = "MuscleSubGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
      private UUID id;

      @Schema(description = "MuscleSubGroup name", example = "Lower chest")
      private String name;

      @Schema(description = "MuscleSubGroup name", example = "Lower part of the pectoral")
      private String description;
    }
  }
}
