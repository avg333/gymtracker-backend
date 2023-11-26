package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.facade.setgroup.SetGroupFacade;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSetGroupDescriptionServiceImpl implements UpdateSetGroupDescriptionService {

  private final SetGroupFacade setGroupFacade;
  private final AuthWorkoutsService authWorkoutsService;

  private static boolean isSameDescription(final String description, final SetGroup setGroup) {
    return StringUtils.equals(setGroup.getDescription(), description);
  }

  @Override
  public String execute(final UUID setGroupId, final String description)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    final SetGroup setGroup = setGroupFacade.getSetGroupWithWorkout(setGroupId);

    authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE);

    if (isSameDescription(description, setGroup)) {
      return description;
    }

    setGroup.setDescription(description);

    return setGroupFacade.saveSetGroup(setGroup).getDescription();
  }
}
