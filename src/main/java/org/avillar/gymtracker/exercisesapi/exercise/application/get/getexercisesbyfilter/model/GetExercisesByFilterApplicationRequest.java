package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetExercisesByFilterApplicationRequest {

  private String name;
  private String description;
  private Boolean unilateral;
  private UUID loadTypeId;
  private List<UUID> muscleSupGroupIds;
  private UUID muscleGroupId;
  private List<UUID> muscleSubGroupIds;
}
