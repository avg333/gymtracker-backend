package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseInfrastructure;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSetGroupResponseApplication {
  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private List<GetWorkoutDetailsResponseInfrastructure.Set> sets;
  private Workout workout;

  @Data
  @NoArgsConstructor
  public static class Workout {
    private UUID id;
  }
}
