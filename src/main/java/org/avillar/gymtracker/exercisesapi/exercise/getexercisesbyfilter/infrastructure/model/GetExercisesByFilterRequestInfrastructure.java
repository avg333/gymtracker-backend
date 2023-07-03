package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetExercisesByFilterRequestInfrastructure {

  private String name;
  private String description;
  private Boolean unilateral;
  private UUID loadTypeId;
  private List<UUID> muscleSupGroupIds;
  private UUID muscleGroupId;
  private List<UUID> muscleSubGroupIds;
}
