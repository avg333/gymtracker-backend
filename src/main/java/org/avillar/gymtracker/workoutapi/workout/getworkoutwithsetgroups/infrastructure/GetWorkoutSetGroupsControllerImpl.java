package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper.GetWorkoutSetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponse;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutSetGroupsControllerImpl implements GetWorkoutSetGroupsController {

  private final GetWorkoutSetGroupsService getWorkoutSetGroupsService;
  private final GetWorkoutSetGroupsControllerMapper getWorkoutSetGroupsControllerMapper;

  public GetWorkoutSetGroupsResponse execute(final UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException {
    return getWorkoutSetGroupsControllerMapper.map(getWorkoutSetGroupsService.execute(workoutId));
  }
}
