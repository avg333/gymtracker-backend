package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model;

import java.util.UUID;
import lombok.Data;

@Data
public class GetSetGroupsByExerciseResponseApplication {

  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private Workout workout;

  @Data
  public static class Workout {
    private UUID id;
  }
}
