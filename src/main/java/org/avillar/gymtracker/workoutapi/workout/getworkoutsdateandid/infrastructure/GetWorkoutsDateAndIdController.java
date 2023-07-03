package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetWorkoutsDateAndIdController {

  @GetMapping("/users/{userId}/workouts/dates")
  ResponseEntity<GetWorkoutsDateAndIdResponseInfrastructure> execute(
      @PathVariable UUID userId, @RequestParam(required = false) UUID exerciseId)
      throws IllegalAccessException;
}
