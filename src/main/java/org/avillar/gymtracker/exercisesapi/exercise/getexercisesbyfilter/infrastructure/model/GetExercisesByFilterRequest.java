package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetExercisesByFilterRequest {

  private String name;
  private String description;
  private Boolean unilateral;
  private List<UUID> loadTypeIds;
  private List<UUID> muscleSupGroupIds;
  private List<UUID> muscleGroupIds;
  private List<UUID> muscleSubGroupIds;
}
