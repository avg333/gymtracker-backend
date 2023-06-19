package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.post.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSetGroupResponseInfrastructure {

  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private Workout workout;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Workout {
    private UUID id;
  }
}
