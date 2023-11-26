package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.set.SetFacade;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper.UpdateSetDataServiceMapper;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetDataServiceImpl implements UpdateSetDataService {

  private final SetFacade setFacade;
  private final AuthWorkoutsService authWorkoutsService;
  private final UpdateSetDataServiceMapper updateSetDataServiceMapper;

  @Override
  public UpdateSetDataResponse execute(
      final UUID setId, final UpdateSetDataRequest updateSetDataRequest)
      throws SetNotFoundException, WorkoutIllegalAccessException {
    final Set set = setFacade.getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    if (isSameData(set, updateSetDataRequest)) {
      return updateSetDataServiceMapper.map(set);
    }

    setRequestData(set, updateSetDataRequest);

    return updateSetDataServiceMapper.map(setFacade.saveSet(set));
  }

  private void setRequestData(final Set set, final UpdateSetDataRequest updateSetDataRequest) {

    set.setDescription(updateSetDataRequest.getDescription());
    set.setWeight(updateSetDataRequest.getWeight());
    set.setReps(updateSetDataRequest.getReps());
    set.setRir(updateSetDataRequest.getRir());
    if (!BooleanUtils.isTrue(updateSetDataRequest.getCompleted())) {
      set.setCompletedAt(null);
    } else if (set.getCompletedAt() == null) {
      set.setCompletedAt(new Date());
    }
  }

  private boolean isSameData(final Set set, final UpdateSetDataRequest updateSetDataRequest) {

    final boolean sameDescription =
        StringUtils.equals(set.getDescription(), updateSetDataRequest.getDescription());
    final boolean sameWeight = set.getWeight().equals(updateSetDataRequest.getWeight());
    final boolean sameRir = set.getRir().equals(updateSetDataRequest.getRir());
    final boolean sameReps = set.getReps().equals(updateSetDataRequest.getReps());
    final boolean sameCompletedAt =
        isSameCompletedAt(updateSetDataRequest.getCompleted(), set.getCompletedAt());
    return sameDescription && sameWeight && sameRir && sameReps && sameCompletedAt;
  }

  private boolean isSameCompletedAt(final Boolean completed, final Date completedAt) {
    return (!BooleanUtils.isTrue(completed) && completedAt == null)
        || (BooleanUtils.isTrue(completed) && completedAt != null);
  }
}
