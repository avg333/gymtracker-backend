package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateWorkoutResponseInfrastructure {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
}
