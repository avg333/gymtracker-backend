package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateSetGroupResponseApplication {

  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private Workout workout;

  @Data
  @AllArgsConstructor
  public static class Workout {
    private UUID id;
  }
}
