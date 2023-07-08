package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model;

import jakarta.validation.constraints.NotNull;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Data
public class UpdateWorkoutDateRequest {

  @NotNull(message = "The date is obligatory")
  @DateTimeFormat(iso = ISO.DATE)
  private Date date;
}
