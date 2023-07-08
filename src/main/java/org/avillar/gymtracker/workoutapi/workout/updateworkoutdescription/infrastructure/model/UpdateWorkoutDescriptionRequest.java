package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateWorkoutDescriptionRequest {

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;
}
