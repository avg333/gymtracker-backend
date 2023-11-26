package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutForDateAlreadyExistsException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.UpdateWorkoutDateControllerDocumentation.Methods.UpdateWorkoutDateDocumentation;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateWorkoutDateController {

  @UpdateWorkoutDateDocumentation
  @PatchMapping("/workouts/{workoutId}/date")
  @ResponseStatus(HttpStatus.OK)
  UpdateWorkoutDateResponse execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody UpdateWorkoutDateRequest updateWorkoutDateRequest)
      throws WorkoutNotFoundException,
          WorkoutForDateAlreadyExistsException,
          WorkoutIllegalAccessException;
}
