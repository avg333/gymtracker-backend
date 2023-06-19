package org.avillar.gymtracker.workoutapi.setgroup.application.get.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.model.GetWorkoutResponseInfrastructure;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSetGroupResponseApplication {
  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private List<GetWorkoutResponseInfrastructure.Set> sets;
  private Workout workout;

  @Data
  @Builder
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

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Workout {
    private UUID id;
  }
}
