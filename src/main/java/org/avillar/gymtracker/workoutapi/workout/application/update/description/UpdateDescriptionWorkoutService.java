package org.avillar.gymtracker.workoutapi.workout.application.update.description;

import java.util.UUID;

public interface UpdateDescriptionWorkoutService {

  String update(final UUID workoutId, final String date);
}
