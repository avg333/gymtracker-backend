package org.avillar.gymtracker.workoutapi.exception.application;

import java.util.Date;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DuplicatedWorkoutDateException extends RuntimeException {

  private final UUID userId;
  private final Date workoutDate;

  public DuplicatedWorkoutDateException(UUID userId, Date workoutDate) {
    super(
        DuplicatedWorkoutDateException.generateMessage(userId.toString(), workoutDate.toString()));
    this.userId = userId;
    this.workoutDate = workoutDate;
  }

  private static String generateMessage(String userId, String workoutDate) {
    return "Already exists a workout with the date: " + workoutDate + " for the user: " + userId;
  }
}
