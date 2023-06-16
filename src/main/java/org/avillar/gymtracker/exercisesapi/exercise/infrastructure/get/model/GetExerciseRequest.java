package org.avillar.gymtracker.exercisesapi.exercise.infrastructure.get.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetExerciseRequest {

  private String name;
  private String description;
  private Boolean unilateral;
  private UUID loadTypeId;
  private List<UUID> muscleSupGroupIds;
  private UUID muscleGroupId;
  private List<UUID> muscleSubGroupIds;
}
