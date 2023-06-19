package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateWorkoutSetGroupsRequestInfrastructure {

  @NotNull private UUID id;

  @NotNull private Source source;

  public enum Source {
    WORKOUT,
    SESSION
  }
}
