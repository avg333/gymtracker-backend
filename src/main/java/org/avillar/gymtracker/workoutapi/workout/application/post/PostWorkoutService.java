package org.avillar.gymtracker.workoutapi.workout.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponseApplication;

public interface PostWorkoutService {

  PostWorkoutResponseApplication execute(
      UUID userId, PostWorkoutRequestApplication postWorkoutRequestApplication);
}
