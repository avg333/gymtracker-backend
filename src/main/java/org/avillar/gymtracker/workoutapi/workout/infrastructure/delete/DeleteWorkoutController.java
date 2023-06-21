package org.avillar.gymtracker.workoutapi.workout.infrastructure.delete;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.delete.DeleteWorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class DeleteWorkoutController {

  private final DeleteWorkoutService deleteWorkoutService;

  @DeleteMapping("/workouts/{workoutId}")
  public ResponseEntity<Void> deleteWorkout(@PathVariable final UUID workoutId) {
    deleteWorkoutService.delete(workoutId);
    return ResponseEntity.noContent().build();
  }
}
