package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class CopySetGroupsRequestInfrastructure {

  @NotNull private UUID id;

  @NotNull private Source source;

  public enum Source {
    WORKOUT,
    SESSION
  }
}
