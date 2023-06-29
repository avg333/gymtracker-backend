package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSetGroupResponseInfrastructure {

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
