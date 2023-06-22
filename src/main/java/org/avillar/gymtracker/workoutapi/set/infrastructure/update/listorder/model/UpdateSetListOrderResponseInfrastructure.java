package org.avillar.gymtracker.workoutapi.set.infrastructure.update.listorder.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetListOrderResponseInfrastructure {

  private List<Set> sets;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
    private SetGroup setGroup;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetGroup {
      private UUID id;
    }
  }
}
