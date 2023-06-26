package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkoutSimpleResponseInfrastructure {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
}
