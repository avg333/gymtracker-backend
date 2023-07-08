package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSubGroupByMuscleGroupResponse {

  private UUID id;
  private String name;
  private String description;
}
