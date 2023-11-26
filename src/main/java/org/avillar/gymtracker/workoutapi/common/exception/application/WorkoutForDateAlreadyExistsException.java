package org.avillar.gymtracker.workoutapi.common.exception.application;

import java.time.LocalDate;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class WorkoutForDateAlreadyExistsException extends Exception {

  private final UUID userId;
  private final LocalDate workoutDate;

  public WorkoutForDateAlreadyExistsException(final UUID userId, final LocalDate workoutDate) {
    super("A workout for the date: " + workoutDate + " already exists for the user: " + userId);
    this.userId = userId;
    this.workoutDate = workoutDate;
  }
}
