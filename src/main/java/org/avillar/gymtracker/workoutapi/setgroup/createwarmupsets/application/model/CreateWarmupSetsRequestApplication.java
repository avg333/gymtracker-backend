package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model;

import lombok.Data;

@Data
public class CreateWarmupSetsRequestApplication {

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
