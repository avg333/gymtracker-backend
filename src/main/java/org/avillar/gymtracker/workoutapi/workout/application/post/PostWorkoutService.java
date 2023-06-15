package org.avillar.gymtracker.workoutapi.workout.application.post;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponse;

public interface PostWorkoutService {

  PostWorkoutResponse post(UUID userId, PostWorkoutRequest postWorkoutRequest);
}
