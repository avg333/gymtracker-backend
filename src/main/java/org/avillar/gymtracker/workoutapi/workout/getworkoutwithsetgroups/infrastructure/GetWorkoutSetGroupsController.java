package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.GetWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper.GetWorkoutSetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetWorkoutSetGroupsController {

  private final GetWorkoutSetGroupsService getWorkoutSetGroupsService;
  private final GetWorkoutSetGroupsControllerMapper getWorkoutSetGroupsControllerMapper;

  /** GetSGFromWorkout. 1 Retorno simple + 1 retorno con SG (depth = 1) */
  @GetMapping("/workouts/{workoutId}/sgs")
  public ResponseEntity<GetWorkoutSetGroupsResponseInfrastructure> get(
      @PathVariable final UUID workoutId) {
    return ResponseEntity.ok(
        getWorkoutSetGroupsControllerMapper.map(getWorkoutSetGroupsService.execute(workoutId)));
  }
}
