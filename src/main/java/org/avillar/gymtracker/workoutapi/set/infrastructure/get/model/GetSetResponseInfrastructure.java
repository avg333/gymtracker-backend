package org.avillar.gymtracker.workoutapi.set.infrastructure.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSetResponseInfrastructure {
  private UUID id;
  private Integer listOrder;
  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
  private SetGroup setGroup;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SetGroup {
    private UUID id;
  }
}
