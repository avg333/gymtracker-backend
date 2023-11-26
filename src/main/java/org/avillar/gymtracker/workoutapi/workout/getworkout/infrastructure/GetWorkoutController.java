package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.GetWorkoutControllerDocumentation.Methods.GetWorkoutDocumentation;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetWorkoutController {

  @GetWorkoutDocumentation
  @GetMapping("/workouts/{workoutId}")
  @ResponseStatus(HttpStatus.OK)
  GetWorkoutResponse execute(@PathVariable UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
