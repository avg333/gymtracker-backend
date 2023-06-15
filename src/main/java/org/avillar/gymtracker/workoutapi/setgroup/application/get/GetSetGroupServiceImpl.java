package org.avillar.gymtracker.workoutapi.setgroup.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.mapper.GetSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponse;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetGroupServiceImpl implements GetSetGroupService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetSetGroupServiceMapper getSetGroupServiceMapper;

  @Override
  public GetSetGroupResponse getSetGroup(final UUID setGroupId, final boolean full) {
    final List<SetGroup> setGroups =
        full
            ? setGroupDao.getSetGroupFullByIds(List.of(setGroupId))
            : setGroupDao.getSetGroupWithWorkoutById(setGroupId);
    final SetGroup setGroup =
        setGroups.stream()
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));

    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    return getSetGroupServiceMapper.getResponse(setGroup); // TODO Arreglar mapeo cuando no es full
  }

  @Override
  public List<GetSetGroupResponse> getAllUserAndExerciseSetGroups(
      final UUID userId, final UUID exerciseId) {
    final Workout workout = new Workout();
    workout.setUserId(userId);
    final SetGroup setGroup = new SetGroup();
    setGroup.setWorkout(workout);
    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    return getSetGroupServiceMapper.getResponse( // FIXME Cambiar respuesta
        setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId));
  }
}
