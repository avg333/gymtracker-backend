package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;

@Data
public class UpdateWorkoutDateRequestInfrastructure {

  @NotNull(message = "The date is obligatory")
  private Date date;
}
