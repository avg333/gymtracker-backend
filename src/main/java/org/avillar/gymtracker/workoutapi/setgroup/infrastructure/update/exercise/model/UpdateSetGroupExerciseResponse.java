package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.exercise.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetGroupExerciseResponse {

  private UUID exerciseId;
}
