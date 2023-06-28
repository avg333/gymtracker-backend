package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutController {
  @GetMapping("/workouts/{workoutId}")
  ResponseEntity<GetWorkoutResponseInfrastructure> get(@PathVariable UUID workoutId)
      throws EntityNotFoundException, IllegalAccessException;
}
