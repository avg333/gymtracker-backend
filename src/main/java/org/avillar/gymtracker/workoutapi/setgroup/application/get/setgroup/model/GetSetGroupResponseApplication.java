package org.avillar.gymtracker.workoutapi.setgroup.application.get.setgroup.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutResponseInfrastructure;

@Data
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
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Workout {
    private UUID id;
  }
}
