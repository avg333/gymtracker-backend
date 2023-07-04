package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetExercisesByFilterRequestApplication {

  private String name;
  private String description;
  private Boolean unilateral;
  private List<UUID> loadTypeIds;
  private List<UUID> muscleSupGroupIds;
  private List<UUID> muscleGroupIds;
  private List<UUID> muscleSubGroupIds;
}
