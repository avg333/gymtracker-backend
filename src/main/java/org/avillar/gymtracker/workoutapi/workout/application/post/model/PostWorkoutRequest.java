package org.avillar.gymtracker.workoutapi.workout.application.post.model;

import java.util.Date;
import lombok.Data;

@Data
public class PostWorkoutRequest {

  private Date date;

  private String description;
}
