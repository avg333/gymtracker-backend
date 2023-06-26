package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.GetWorkoutSimpleService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple.mapper.GetWorkoutSimpleControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple.model.GetWorkoutSimpleResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetWorkoutSimpleController {

  private final GetWorkoutSimpleService getWorkoutSimpleServiceService;
  private final GetWorkoutSimpleControllerMapper getWorkoutSimpleControllerMapper;

  /** Casos de uso: Pantalla Exercises. Retorno simple Change WorkoutDate. Retorno simple */
  @GetMapping("/workouts/{workoutId}/simple")
  public ResponseEntity<GetWorkoutSimpleResponseInfrastructure> get(
      @PathVariable final UUID workoutId) {
    return ResponseEntity.ok(
        getWorkoutSimpleControllerMapper.map(getWorkoutSimpleServiceService.execute(workoutId)));
  }
}
