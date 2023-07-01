package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.sets.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSetGroupSetsResponseInfrastructure {

  private List<Set> sets;

  @Data
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
  }
}
