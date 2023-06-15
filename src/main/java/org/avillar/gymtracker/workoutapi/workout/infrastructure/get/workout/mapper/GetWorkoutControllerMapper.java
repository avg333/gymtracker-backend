package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.mapper;

import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.model.GetWorkoutResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutControllerMapper {

  GetWorkoutResponse getResponse(
      org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponse
          getWorkoutResponse);
}
