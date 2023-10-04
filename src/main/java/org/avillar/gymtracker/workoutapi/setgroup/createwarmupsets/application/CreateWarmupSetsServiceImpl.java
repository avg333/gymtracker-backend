package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsRequestApplication;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.application.model.CreateWarmupSetsResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateWarmupSetsServiceImpl implements CreateWarmupSetsService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;

  private static final double[] LIGHT_PERCENTAGES = { 0.6};
  private static final double[] MODERATE_PERCENTAGES = { 0.6, 0.8};
  private static final double[] HEAVY_PERCENTAGES = { 0.6, 0.8, 1.0};

  @Override
  public CreateWarmupSetsResponseApplication execute(
      final UUID setGroupId, final CreateWarmupSetsRequestApplication request) {

    final SetGroup setGroup = getSetGroupFull(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);



    return null;
  }

  private SetGroup getSetGroupFull(final UUID setGroupId) {
    return setGroupDao.getSetGroupFullByIds(List.of(setGroupId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }

  private static double get1RM(final double weight, final int reps) {
    return weight / (1.0278 - 0.0278 * reps);
  }
}
