package org.avillar.gymtracker.workoutapi.set.getset.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.set.getset.application.mapper.GetSetServiceMapper;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetServiceImpl implements GetSetService {

  private final SetDao setDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetSetServiceMapper getSetServiceMapper;

  @Override
  public GetSetResponseApplication execute(final UUID setId) {
    final Set set = getSetFull(setId);

    authWorkoutsService.checkAccess(set, AuthOperations.READ);

    return getSetServiceMapper.map(set);
  }

  private Set getSetFull(final UUID setId) {
    return setDao.getSetFullById(setId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Set.class, setId));
  }
}
