package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class CreateWorkoutRequestInfrastructure {

  @NotNull(message = "The date is obligatory")
  @DateTimeFormat(iso = ISO.DATE)
  private Date date;

  @Size(max = 255, message = "The description must have a length between 0 and 255 characters")
  private String description;
}
