package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateSetGroupExerciseRequestInfrastructure {

  @NotNull private UUID exerciseId;
}
