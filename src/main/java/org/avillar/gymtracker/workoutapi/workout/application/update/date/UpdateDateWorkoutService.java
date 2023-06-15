package org.avillar.gymtracker.workoutapi.workout.application.update.date;

import java.util.Date;
import java.util.UUID;

public interface UpdateDateWorkoutService {

  Date update(final UUID workoutId, final Date date);
}
