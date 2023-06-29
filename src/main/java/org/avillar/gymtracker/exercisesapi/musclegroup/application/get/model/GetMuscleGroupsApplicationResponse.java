package org.avillar.gymtracker.exercisesapi.musclegroup.application.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMuscleGroupsApplicationResponse {

  private UUID id;
  private String name;
  private String description;
}
