package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class GetWorkoutResponse {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
}
