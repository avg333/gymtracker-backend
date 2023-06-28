package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetWorkoutResponseInfrastructure {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
}
