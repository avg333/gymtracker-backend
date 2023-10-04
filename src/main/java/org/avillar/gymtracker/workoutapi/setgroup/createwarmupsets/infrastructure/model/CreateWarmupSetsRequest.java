package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model;

import lombok.Data;

@Data
public class CreateWarmupSetsRequest {

  private Set set;

  private Exhaustiveness exhaustiveness;

  @Data
  public static class Set {
    private Integer reps;
    private Double rir;
    private Double weight;
  }

  public enum Exhaustiveness {
    LIGHT,
    MODERATE,
    HEAVY
  }
}
