package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSetGroupResponseApplication {
  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private Workout workout;

  @Data
  @NoArgsConstructor
  public static class Workout {
    private UUID id;
  }
}
