package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.GetWorkoutDetailsControllerDocumentation.Methods.GetWorkoutDetailsDocumentation;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetWorkoutDetailsController {

  @GetWorkoutDetailsDocumentation
  @GetMapping("/workouts/{workoutId}/details")
  @ResponseStatus(HttpStatus.OK)
  GetWorkoutDetailsResponseDto execute(@PathVariable final UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
