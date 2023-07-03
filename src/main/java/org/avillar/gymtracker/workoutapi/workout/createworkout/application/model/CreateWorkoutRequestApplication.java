package org.avillar.gymtracker.workoutapi.workout.createworkout.application.model;

import java.util.Date;
import lombok.Data;

@Data
public class CreateWorkoutRequestApplication {

  private Date date;
  private String description;
}
