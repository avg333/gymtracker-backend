package org.avillar.gymtracker.workoutapi.workout.getworkout.application.model;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class GetWorkoutResponseApplication {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
}
