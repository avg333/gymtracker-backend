package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;
import org.avillar.gymtracker.workoutapi.workout.WorkoutControllerDocumentation.WorkoutControllerTag;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.GetWorkoutSetGroupsControllerDocumentation.Methods.GetWorkoutSetGroupsDocumentation;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@WorkoutControllerTag
@RequestMapping(path = "${workoutsApiPrefix}/v1")
public interface GetWorkoutSetGroupsController {

  @GetWorkoutSetGroupsDocumentation
  @GetMapping("/workouts/{workoutId}/sgs") // TODO Define better this endpoint
  @ResponseStatus(HttpStatus.OK)
  GetWorkoutSetGroupsResponse execute(@PathVariable UUID workoutId)
      throws WorkoutNotFoundException, WorkoutIllegalAccessException;
}
