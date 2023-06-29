package org.avillar.gymtracker.workoutapi.set.createset.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.set.createset.application.mapper.CreateSetServiceMapper;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSetServiceImpl implements CreateSetService {

  private final SetDao setDao;
  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final CreateSetServiceMapper createSetServiceMapper;

  @Override
  public CreateSetResponseApplication execute(
      final UUID setGroupId, final CreateSetRequestApplication createSetRequestApplication)
      throws EntityNotFoundException, IllegalAccessException {
    final SetGroup setGroup = getSetGroupFull(setGroupId);

    final Set set = createSetServiceMapper.map(createSetRequestApplication);
    set.setSetGroup(setGroup);
    set.setListOrder(setGroup.getSets().size());

    authWorkoutsService.checkAccess(set, AuthOperations.CREATE);

    setDao.save(set);

    return createSetServiceMapper.map(set);
  }

  private SetGroup getSetGroupFull(final UUID setGroupId) {
    return setGroupDao.getSetGroupFullByIds(List.of(setGroupId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
