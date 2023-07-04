package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetSetGroupsByExerciseResponseApplication {

  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private Workout workout;
  private List<Set> sets;

  @Data
  public static class Workout {
    private UUID id;
  }

  @Data
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
  }
}
