package org.avillar.gymtracker.workoutapi.setgroup.application.update.description;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupDescriptionServiceImpl implements UpdateSetGroupDescriptionService {

  private final SetGroupDao setGroupDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public String execute(final UUID setGroupId, final String description) {
    final SetGroup setGroup = getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    if (StringUtils.equals(setGroup.getDescription(), description)) {
      return setGroup.getDescription();
    }

    setGroup.setDescription(description);

    setGroupDao.save(setGroup);

    return setGroup.getDescription();
  }

  private SetGroup getSetGroupWithWorkout(final UUID setGroupId) {
    return setGroupDao.getSetGroupWithWorkoutById(setGroupId).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(SetGroup.class, setGroupId));
  }
}
