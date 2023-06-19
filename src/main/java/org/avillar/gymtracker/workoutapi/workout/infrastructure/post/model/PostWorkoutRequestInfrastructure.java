package org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;

@Data
public class PostWorkoutRequestInfrastructure {

  @NotNull(message = "The date is obligatory")
  private Date date;

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;
}
