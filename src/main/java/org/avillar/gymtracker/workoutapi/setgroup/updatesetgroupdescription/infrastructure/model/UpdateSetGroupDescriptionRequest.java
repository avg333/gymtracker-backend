package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateSetGroupDescriptionRequest {

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;
}
