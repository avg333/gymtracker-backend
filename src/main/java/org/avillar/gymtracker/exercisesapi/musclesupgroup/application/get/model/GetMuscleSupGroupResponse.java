package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMuscleSupGroupResponse {

  private UUID id;
  private String name;
  private String description;
}
