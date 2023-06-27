package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisebyid.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExerciseApplicationRequest {

  private String name;
  private String description;
  private Boolean unilateral;
  private UUID loadTypeId;
  private List<UUID> muscleSupGroupIds;
  private UUID muscleGroupId;
  private List<UUID> muscleSubGroupIds;
}
