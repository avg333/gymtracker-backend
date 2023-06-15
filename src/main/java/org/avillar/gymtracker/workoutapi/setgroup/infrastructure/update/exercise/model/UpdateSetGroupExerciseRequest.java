package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.exercise.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateSetGroupExerciseRequest {

  @NotNull private UUID exerciseId;
}
