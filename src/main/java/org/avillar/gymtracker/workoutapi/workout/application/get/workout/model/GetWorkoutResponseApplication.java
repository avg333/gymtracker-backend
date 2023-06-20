package org.avillar.gymtracker.workoutapi.workout.application.get.workout.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkoutResponseApplication {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
  private List<SetGroup> setGroups;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
    private List<Set> sets;

    private GetExerciseResponse exercise;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
  }
}
