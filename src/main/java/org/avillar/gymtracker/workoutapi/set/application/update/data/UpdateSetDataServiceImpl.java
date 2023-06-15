package org.avillar.gymtracker.workoutapi.set.application.update.data;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.update.data.mapper.UpdateSetDataServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponse;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
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
  public UpdateSetDataResponse update(
      final UUID setId, final UpdateSetDataRequest updateSetDataRequest) {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.UPDATE);

    if (set.getDescription().equals(updateSetDataRequest.getDescription())
        && set.getWeight().equals(updateSetDataRequest.getWeight())
        && set.getRir().equals(updateSetDataRequest.getRir())
        && set.getReps().equals(updateSetDataRequest.getReps())) {
      return updateSetDataServiceMapper.updateResponse(set);
    }

    set.setDescription(updateSetDataRequest.getDescription());
    set.setWeight(updateSetDataRequest.getWeight());
    set.setReps(updateSetDataRequest.getReps());
    set.setRir(updateSetDataRequest.getRir());

    setDao.save(set);

    return updateSetDataServiceMapper.updateResponse(set);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
