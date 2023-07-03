package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.mapper.GetSetGroupServiceMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.model.GetSetGroupResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSetGroupServiceImpl implements GetSetGroupService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetSetGroupServiceMapper getSetGroupServiceMapper;

  @Override
  public GetSetGroupResponseApplication execute(final UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException {
    final SetGroup setGroup =
        setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
            .findAny()
            .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));

    authWorkoutsService.checkAccess(setGroup, AuthOperations.READ);

    return getSetGroupServiceMapper.map(setGroup);
  }
}
