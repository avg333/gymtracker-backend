package org.avillar.gymtracker.workoutapi.workout.application.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.workoutapi.workout.application.post.mapper.PostWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponse;
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
  public PostWorkoutResponse post(final UUID userId, final PostWorkoutRequest postWorkoutRequest) {

    if (workoutDao.existsWorkoutByUserAndDate(userId, postWorkoutRequest.getDate())) {
      throw new DuplicatedWorkoutDateException(userId, postWorkoutRequest.getDate());
    }

    final Workout workout = postWorkoutServiceMapper.postRequest(postWorkoutRequest);
    workout.setUserId(userId);

    authWorkoutsService.checkAccess(workout, AuthOperations.CREATE);

    workoutDao.save(workout);

    return postWorkoutServiceMapper.postResponse(workout);
  }
}
