package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper.GetWorkoutSetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetWorkoutSetGroupsControllerImpl implements GetWorkoutSetGroupsController {

  private final GetWorkoutSetGroupsService getWorkoutSetGroupsService;
  private final GetWorkoutSetGroupsControllerMapper getWorkoutSetGroupsControllerMapper;

  public ResponseEntity<GetWorkoutSetGroupsResponseInfrastructure> get(final UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException {
    return ResponseEntity.ok(
        getWorkoutSetGroupsControllerMapper.map(getWorkoutSetGroupsService.execute(workoutId)));
  }
}
