package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.application.GetSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.mapper.GetSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetSetGroupControllerImpl implements GetSetGroupController {

  private final GetSetGroupService getSetGroupService;
  private final GetSetGroupControllerMapper getSetGroupControllerMapper;

  @Override
  public GetSetGroupResponse execute(final UUID setGroupId)
      throws SetGroupNotFoundException, WorkoutIllegalAccessException {
    return getSetGroupControllerMapper.map(getSetGroupService.execute(setGroupId));
  }
}
