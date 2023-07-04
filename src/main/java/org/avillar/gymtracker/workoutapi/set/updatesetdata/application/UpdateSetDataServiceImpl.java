package org.avillar.gymtracker.workoutapi.set.updatesetdata.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

  @Override
  @Transactional
  public UpdateSetDataResponseApplication execute(
      final UUID setId, final UpdateSetDataRequestApplication updateSetDataRequestApplication)
      throws EntityNotFoundException, IllegalAccessException {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    if (StringUtils.equals(set.getDescription(), updateSetDataRequestApplication.getDescription())
        && set.getWeight().equals(updateSetDataRequestApplication.getWeight())
        && set.getRir().equals(updateSetDataRequestApplication.getRir())
        && set.getReps().equals(updateSetDataRequestApplication.getReps())) {
      return updateSetDataServiceMapper.map(set);
    }

    set.setDescription(updateSetDataRequestApplication.getDescription());
    set.setWeight(updateSetDataRequestApplication.getWeight());
    set.setReps(updateSetDataRequestApplication.getReps());
    set.setRir(updateSetDataRequestApplication.getRir());

    setDao.save(set);

    return updateSetDataServiceMapper.map(set);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}