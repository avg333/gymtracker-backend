package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper.UpdateSetDataServiceMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequestApplication;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateSetDataServiceImpl implements UpdateSetDataService {

  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateSetDataServiceMapper updateSetDataServiceMapper;

  private static boolean isSameCompletedAt(final Boolean completed, final Date completedAt) {
    return (!BooleanUtils.isTrue(completed) && completedAt == null)
        || (BooleanUtils.isTrue(completed) && completedAt != null);
  }

  @Override
  @Transactional
  public UpdateSetDataResponseApplication execute(
      final UUID setId, final UpdateSetDataRequestApplication updateSetDataRequestApplication)
      throws EntityNotFoundException, IllegalAccessException {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    final boolean sameDescription =
        StringUtils.equals(set.getDescription(), updateSetDataRequestApplication.getDescription());
    final boolean sameWeight = set.getWeight().equals(updateSetDataRequestApplication.getWeight());
    final boolean sameRir = set.getRir().equals(updateSetDataRequestApplication.getRir());
    final boolean sameReps = set.getReps().equals(updateSetDataRequestApplication.getReps());
    final boolean sameCompletedAt =
        isSameCompletedAt(updateSetDataRequestApplication.getCompleted(), set.getCompletedAt());
    if (sameDescription && sameWeight && sameRir && sameReps && sameCompletedAt) {
      return updateSetDataServiceMapper.map(set);
    }

    set.setDescription(updateSetDataRequestApplication.getDescription());
    set.setWeight(updateSetDataRequestApplication.getWeight());
    set.setReps(updateSetDataRequestApplication.getReps());
    set.setRir(updateSetDataRequestApplication.getRir());
    if (!BooleanUtils.isTrue(updateSetDataRequestApplication.getCompleted())) {
      set.setCompletedAt(null);
    } else if (set.getCompletedAt() == null) {
      set.setCompletedAt(new Date());
    }

    setDao.save(set);

    return updateSetDataServiceMapper.map(set);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
