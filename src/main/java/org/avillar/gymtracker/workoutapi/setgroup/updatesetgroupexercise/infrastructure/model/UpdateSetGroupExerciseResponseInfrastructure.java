package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetGroupExerciseResponseInfrastructure {

  private UUID exerciseId;
}
