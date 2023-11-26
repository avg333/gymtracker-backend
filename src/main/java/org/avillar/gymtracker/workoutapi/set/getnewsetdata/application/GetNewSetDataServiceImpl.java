package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application;

import java.util.Comparator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNewSetDataServiceImpl implements GetNewSetDataService {

  private final SetFacade setFacade;
  private final SetGroupFacade setGroupFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Set execute(final UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = setGroupFacade.getSetGroupFull(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    // TODO Improve this logic
    return setGroup.getSets().stream()
        .max(Comparator.comparingInt(Set::getListOrder))
        .orElse(setFacade.getSetGroupExerciseHistory(setGroup));
  }
}
