package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model;

import java.util.UUID;
import lombok.Data;

@Data
public class UpdateSetGroupListOrderResponse {

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
