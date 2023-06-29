package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateSetResponseInfrastructure {

  private UUID id;
  private Integer listOrder;
  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;
  private SetGroup setGroup;

  @Data
  @NoArgsConstructor
  public static class SetGroup {
    private UUID id;
  }
}
