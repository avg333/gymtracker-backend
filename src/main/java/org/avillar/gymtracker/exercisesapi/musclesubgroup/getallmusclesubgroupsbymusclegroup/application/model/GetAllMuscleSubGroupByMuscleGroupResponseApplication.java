package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.application.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSubGroupByMuscleGroupResponseApplication implements Serializable {

  private UUID id;
  private String name;
  private String description;
}
