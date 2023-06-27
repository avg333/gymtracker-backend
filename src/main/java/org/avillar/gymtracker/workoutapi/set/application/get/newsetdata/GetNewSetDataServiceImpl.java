package org.avillar.gymtracker.workoutapi.set.application.get.newsetdata;

import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.mapper.GetNewSetDataServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class GetNewSetDataServiceImpl implements GetNewSetDataService {

  private final SetDao setDao;
  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetNewSetDataServiceMapper getNewSetDataServiceMapper;

  @Override
  public GetNewSetDataResponseApplication execute(final UUID setGroupId) {
    final SetGroup setGroup = getSetGroupFull(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    final Optional<Set> set =
        setGroup.getSets().stream().max(Comparator.comparingInt(Set::getListOrder));
    if (set.isPresent()) {
      return getNewSetDataServiceMapper.map(set.get());
    }

    return getNewSetDataServiceMapper.map(
        setDao.findLastSetForExerciseAndUserAux(setGroup.getId()).stream()
            .findAny()
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        Set.class, "setGroupId", setGroupId))); // TODO Arreglar esta excepcion
  }

  private SetGroup getSetGroupFull(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
