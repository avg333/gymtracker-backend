package org.avillar.gymtracker.workoutapi.workout.application.get.idanddate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public interface GetWorkoutIdAndDateService {

  Map<Date, UUID> getAllUserWorkoutDates(UUID userId);

  Map<Date, UUID> getAllUserWorkoutsWithExercise(UUID userId, UUID exerciseId);
}
