package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetExercisesByFilterRequestApplication {

  private String name;
  private String description;
  private Boolean unilateral;
  private UUID loadTypeId;
  private List<UUID> muscleSupGroupIds;
  private UUID muscleGroupId;
  private List<UUID> muscleSubGroupIds;
}
