package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model;

public record CreateWarmupSetsRequest(Set set, Exhaustiveness exhaustiveness) {

  public enum Exhaustiveness {
    LIGHT,
    MODERATE,
    HEAVY
  }

  public record Set(Integer reps, Double rir, Double weight) {}
}
