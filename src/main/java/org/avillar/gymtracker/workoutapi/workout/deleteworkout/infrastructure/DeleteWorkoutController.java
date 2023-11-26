package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure.DeleteWorkoutControllerDocumentation.Methods.DeleteWorkoutDocumentation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface DeleteWorkoutController {

  @DeleteWorkoutDocumentation
  @DeleteMapping("/workouts/{workoutId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Void execute(@PathVariable UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
