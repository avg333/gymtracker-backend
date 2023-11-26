package org.avillar.gymtracker.workoutapi.set.createwarmupsets.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWarmupSetsServiceImpl implements CreateWarmupSetsService {

  private static final double[] LIGHT_PERCENTAGES = {0.6};
  private static final double[] MODERATE_PERCENTAGES = {0.6, 0.8};
  private static final double[] HEAVY_PERCENTAGES = {0.6, 0.8, 1.0};

  private final SetGroupFacade setGroupFacade;
  private final AuthWorkoutsService authWorkoutsService;

  private static double get1RM(final double weight, final int reps) {
    return weight / (1.0278 - 0.0278 * reps);
  }

  @Override
  public List<Set> execute(
      final UUID setGroupId, final Set set, final Exhaustiveness exhaustiveness)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {

    final SetGroup setGroup = setGroupFacade.getSetGroupFull(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    return setGroup.getSets();
  }

  public enum Exhaustiveness {
    LIGHT,
    MODERATE,
    HEAVY
  }
}
