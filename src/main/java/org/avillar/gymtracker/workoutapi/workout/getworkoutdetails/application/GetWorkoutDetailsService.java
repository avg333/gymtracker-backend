package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;

public interface GetWorkoutDetailsService {

  GetWorkoutDetailsResponseApplication execute(UUID workoutId);
}
