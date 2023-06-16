package org.avillar.gymtracker.exercisesapi.musclesubgroup.application.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMuscleSubGroupResponse {

  private UUID id;
  private String name;
  private String description;
}
