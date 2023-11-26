package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.CreateWorkoutControllerDocumentation.Methods.CreateWorkoutDocumentation;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface CreateWorkoutController {

  @CreateWorkoutDocumentation
  @PostMapping("/users/{userId}/workouts")
  @ResponseStatus(HttpStatus.OK)
  CreateWorkoutResponse execute(
      @PathVariable UUID userId, @Valid @RequestBody CreateWorkoutRequest createWorkoutRequest)
      throws WorkoutIllegalAccessException, WorkoutForDateAlreadyExistsException;
}
