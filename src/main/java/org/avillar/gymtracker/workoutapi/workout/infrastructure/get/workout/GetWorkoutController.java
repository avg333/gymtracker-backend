package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.mapper.GetWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.model.GetWorkoutResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// FINALIZAR
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetWorkoutController {

  private final GetWorkoutService getWorkoutService;
  private final GetWorkoutControllerMapper getWorkoutControllerMapper;

  /** SetGroupContainer. Retorno FULL */
  @GetMapping("/workouts/{workoutId}")
  public ResponseEntity<GetWorkoutResponseInfrastructure> get(@PathVariable final UUID workoutId) {
    return ResponseEntity.ok(getWorkoutControllerMapper.map(getWorkoutService.execute(workoutId)));
  }
}
