package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.GetWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.mapper.GetWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.model.GetWorkoutResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetWorkoutController {

  private final GetWorkoutService getWorkoutService;
  private final GetWorkoutControllerMapper getWorkoutControllerMapper;

  /**
   * Casos de uso: Pantalla Exercises. Retorno simple Change WorkoutDate. Retorno simple
   * GetSGFromWorkout. 1 Retorno simple + 1 retorno con SG (depth = 1) SetGroupContainer. Retorno
   * FULL
   */
  @GetMapping("/workouts/{workoutId}")
  public ResponseEntity<GetWorkoutResponse> getWorkoutById(
      @PathVariable final UUID workoutId, @RequestParam(required = false) final boolean full) {
    return ResponseEntity.ok(
        getWorkoutControllerMapper.getResponse(getWorkoutService.getWorkout(workoutId, full)));
  }
}
