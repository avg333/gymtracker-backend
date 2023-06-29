package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSetGroupResponseInfrastructure {

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
