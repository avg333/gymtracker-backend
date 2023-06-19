package org.avillar.gymtracker.workoutapi.set.application.get;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.get.mapper.GetSetServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.get.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetServiceImpl implements GetSetService {

  private final SetDao setDao;
  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetSetServiceMapper getSetServiceMapper;

  @Override
  public GetSetResponseApplication getSet(final UUID setId) {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.READ);

    return getSetServiceMapper.getResponse(set);
  }

  @Override
  public GetSetResponseApplication getSetDefaultDataForNewSet(final UUID setGroupId) {
    final SetGroup setGroup = getSetGroupFull(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    final Optional<Set> set =
        setDao
            .findLastSetForExerciseAndUser(
                setGroup.getWorkout().getUserId(),
                setGroup.getExerciseId(),
                setGroup.getWorkout().getDate())
            .stream()
            .findAny();
    if (set.isPresent()) {
      return getSetServiceMapper.getResponse(set.get());
    }

    return getSetServiceMapper.getResponse(
        setDao.findLastSetForExerciseAndUserAux(setGroup.getId()).stream()
            .findAny()
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        Set.class, "setGroupId", setGroupId))); // TODO Arreglar esta excepcion
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }

  private SetGroup getSetGroupFull(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
