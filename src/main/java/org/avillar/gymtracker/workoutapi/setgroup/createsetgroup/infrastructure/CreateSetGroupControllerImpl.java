package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.application.CreateSetGroupService;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.mapper.CreateSetGroupControllerMapper;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateSetGroupControllerImpl implements CreateSetGroupController {

  private final CreateSetGroupService createSetGroupService;
  private final CreateSetGroupControllerMapper createSetGroupControllerMapper;

  @Override
  public CreateSetGroupResponse execute(
      final UUID workoutId, final CreateSetGroupRequest createSetGroupRequest)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException, ExerciseUnavailableException {
    return createSetGroupControllerMapper.map(
        createSetGroupService.execute(
            workoutId, createSetGroupControllerMapper.map(createSetGroupRequest)));
  }
}
