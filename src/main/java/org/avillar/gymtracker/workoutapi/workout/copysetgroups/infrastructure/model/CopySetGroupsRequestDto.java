package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CopySetGroupsRequestDto(@NotNull UUID id, @NotNull Source source) {

  public enum Source {
    WORKOUT,
    SESSION
  }
}
