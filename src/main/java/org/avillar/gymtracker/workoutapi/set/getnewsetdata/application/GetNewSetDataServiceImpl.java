package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.mapper.GetNewSetDataServiceMapper;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNewSetDataServiceImpl implements GetNewSetDataService {

  private final SetDao setDao;
  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetNewSetDataServiceMapper getNewSetDataServiceMapper;

  @Override
  public GetNewSetDataResponseApplication execute(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    final SetGroup setGroup = getSetGroupFull(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    final Optional<Set> set =
        setGroup.getSets().stream().max(Comparator.comparingInt(Set::getListOrder));
    if (set.isPresent()) {
      return getNewSetDataServiceMapper.map(set.get());
    }

    // TODO Definir bien la logica de negocio
    return getNewSetDataServiceMapper.map(getSetGroupExerciseHistory(setGroup));
  }

  private SetGroup getSetGroupFull(final UUID setGroupId) {
    return setGroupDao.getSetGroupFullByIds(List.of(setGroupId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }

  private Set getSetGroupExerciseHistory(final SetGroup setGroup) {
    return setDao
        .findLastSetForExerciseAndUserAux(
            setGroup.getWorkout().getUserId(),
            setGroup.getExerciseId(),
            setGroup.getWorkout().getDate())
        .stream()
        .findAny()
        .orElseThrow( // TODO Mejorar esta excepcion
            () -> new EntityNotFoundException(Set.class, "exerciseId", setGroup.getExerciseId()));
  }
}
