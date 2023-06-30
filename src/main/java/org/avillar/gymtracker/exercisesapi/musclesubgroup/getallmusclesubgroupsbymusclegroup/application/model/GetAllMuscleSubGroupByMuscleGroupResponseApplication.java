package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSubGroupByMuscleGroupResponseApplication {

  private UUID id;
  private String name;
  private String description;
}
