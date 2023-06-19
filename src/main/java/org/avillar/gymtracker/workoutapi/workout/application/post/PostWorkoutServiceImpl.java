package org.avillar.gymtracker.workoutapi.workout.application.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.workout.application.post.mapper.PostWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWorkoutServiceImpl implements PostWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final PostWorkoutServiceMapper postWorkoutServiceMapper;

  @Override
  public PostWorkoutResponseApplication post(
      final UUID userId, final PostWorkoutRequestApplication postWorkoutRequestApplication) {

    if (workoutDao.existsWorkoutByUserAndDate(userId, postWorkoutRequestApplication.getDate())) {
      throw new DuplicatedWorkoutDateException(userId, postWorkoutRequestApplication.getDate());
    }

    final Workout workout = postWorkoutServiceMapper.postRequest(postWorkoutRequestApplication);
    workout.setUserId(userId);

    authWorkoutsService.checkAccess(workout, AuthOperations.CREATE);

    workoutDao.save(workout);

    return postWorkoutServiceMapper.postResponse(workout);
  }
}
