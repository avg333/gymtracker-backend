package org.avillar.gymtracker.workoutapi.set.getset.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetServiceImpl implements GetSetService {

  private final SetFacade setFacade;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public Set execute(final UUID setId) throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = setFacade.getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.READ);

    return set;
  }
}
