package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateSetListOrderResponseInfrastructure {

  private List<Set> sets;

  @Data
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
    private SetGroup setGroup;

    @Data
    public static class SetGroup {
      private UUID id;
    }
  }
}
