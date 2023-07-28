package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSubGroupByMuscleGroupResponse {

  @Schema(description = "MuscleSubGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
  private UUID id;

  @Schema(description = "MuscleSubGroup name", example = "Lower chest")
  private String name;

  @Schema(description = "MuscleSubGroup name", example = "Lower part of the pectoral")
  private String description;
}
