package org.avillar.gymtracker.workoutapi.workout.application.post.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostWorkoutResponse {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
}
