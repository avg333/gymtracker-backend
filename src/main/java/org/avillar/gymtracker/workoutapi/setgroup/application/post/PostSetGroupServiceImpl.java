package org.avillar.gymtracker.workoutapi.setgroup.application.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.mapper.PostSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.application.post.model.PostSetGroupResponse;
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

  @Override
  public PostSetGroupResponse post(
      final UUID workoutId, final PostSetGroupRequest postSetGroupRequest) {
    final Workout workout = getWorkoutWithSetGroups(workoutId);

    final SetGroup setGroup = postSetGroupServiceMapper.postRequest(postSetGroupRequest);
    setGroup.setWorkout(workout);
    setGroup.setListOrder(workout.getSetGroups().size());

    authWorkoutsService.checkAccess(setGroup, AuthOperations.CREATE);

    setGroupDao.save(setGroup);

    return postSetGroupServiceMapper.postResponse(setGroup);
  }

  private Workout getWorkoutWithSetGroups(final UUID workoutId) {
    return workoutDao.getWorkoutWithSetGroupsById(workoutId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
