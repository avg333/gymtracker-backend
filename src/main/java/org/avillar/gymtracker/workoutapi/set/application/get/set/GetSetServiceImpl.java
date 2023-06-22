package org.avillar.gymtracker.workoutapi.set.application.get.set;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.application.get.set.mapper.GetSetServiceMapper;
import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetServiceImpl implements GetSetService {

  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetSetServiceMapper getSetServiceMapper;

  @Override
  public GetSetResponseApplication getSet(final UUID setId) {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.READ);

    return getSetServiceMapper.getResponse(set);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
