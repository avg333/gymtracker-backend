package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSetGroupExerciseResponse {

  private UUID exerciseId;
}
