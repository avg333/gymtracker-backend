package org.avillar.gymtracker.exercisesapi.musclesubgroup.infrastructure.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMuscleSubGroupsInfrastructureResponse {

  private UUID id;
  private String name;
  private String description;
}
