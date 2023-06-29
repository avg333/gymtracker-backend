package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSetGroupSetsResponseApplication {

  private List<Set> sets;

  @Data
  @AllArgsConstructor
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
  }
}
