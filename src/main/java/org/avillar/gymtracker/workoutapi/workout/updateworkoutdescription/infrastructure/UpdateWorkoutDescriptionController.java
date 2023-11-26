package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.UpdateWorkoutDescriptionControllerDocumentation.Methods.UpdateWorkoutDescriptionDocumentation;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionRequest;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface UpdateWorkoutDescriptionController {

  @UpdateWorkoutDescriptionDocumentation
  @PatchMapping("/workouts/{workoutId}/description")
  @ResponseStatus(HttpStatus.OK)
  UpdateWorkoutDescriptionResponse execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody UpdateWorkoutDescriptionRequest updateWorkoutDescriptionRequest)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
