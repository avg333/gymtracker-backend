package org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMuscleGroupResponse {

  private UUID id;
  private String name;
  private String description;
}
