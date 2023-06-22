package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.idanddate;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.get.idanddate.GetWorkoutIdAndDateService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.idanddate.model.GetWorkoutIdAndDateResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class GetWorkoutIdAndDateController {

  private final GetWorkoutIdAndDateService getWorkoutIdAndDateService;

  @GetMapping("/users/{userId}/workouts/dates")
  public ResponseEntity<GetWorkoutIdAndDateResponseInfrastructure> get(
      @PathVariable final UUID userId, @RequestParam(required = false) final UUID exerciseId) {
    return ResponseEntity.ok(
        new GetWorkoutIdAndDateResponseInfrastructure(
            Objects.nonNull(exerciseId)
                ? getWorkoutIdAndDateService.getAllUserWorkoutsWithExercise(userId, exerciseId)
                : getWorkoutIdAndDateService.getAllUserWorkoutDates(userId)));
  }
}
