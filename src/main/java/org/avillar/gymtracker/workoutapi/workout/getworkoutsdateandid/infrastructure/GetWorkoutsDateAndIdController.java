package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.GetWorkoutsDateAndIdControllerDocumentation.Methods.GetWorkoutsDateAndIdDocumentation;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetWorkoutsDateAndIdController {

  @GetWorkoutsDateAndIdDocumentation
  @GetMapping("/users/{userId}/workouts/dates")
  @ResponseStatus(HttpStatus.OK)
  GetWorkoutsDateAndIdResponse execute(
      @PathVariable UUID userId, @RequestParam(required = false) UUID exerciseId)
      throws WorkoutIllegalAccessException;
}
