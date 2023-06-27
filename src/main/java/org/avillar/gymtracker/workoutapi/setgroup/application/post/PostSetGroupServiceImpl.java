package org.avillar.gymtracker.workoutapi.setgroup.application.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.mapper.PostSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSetGroupServiceImpl implements PostSetGroupService {

  private final SetGroupDao setGroupDao;
  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final PostSetGroupServiceMapper postSetGroupServiceMapper;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public PostSetGroupResponseApplication execute(
      final UUID workoutId, final PostSetGroupRequestApplication postSetGroupRequestApplication) {
    final Workout workout = getWorkoutWithSetGroups(workoutId);

    final SetGroup setGroup = postSetGroupServiceMapper.map(postSetGroupRequestApplication);
    setGroup.setWorkout(workout);
    setGroup.setListOrder(workout.getSetGroups().size());

    if (!exerciseRepositoryClient.canAccessExerciseById(setGroup.getExerciseId())) {
      throw new EntityNotFoundException(Exercise.class, setGroup.getExerciseId());
    }

    authWorkoutsService.checkAccess(setGroup, AuthOperations.CREATE);

    setGroupDao.save(setGroup);

    return postSetGroupServiceMapper.map(setGroup);
  }

  private Workout getWorkoutWithSetGroups(final UUID workoutId) {
    return workoutDao.getWorkoutWithSetGroupsById(workoutId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
