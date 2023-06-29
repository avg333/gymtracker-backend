package org.avillar.gymtracker.exercisesapi.musclegroup.infrastructure.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMuscleGroupsInfrastructureResponse {

  private UUID id;
  private String name;
  private String description;
}
