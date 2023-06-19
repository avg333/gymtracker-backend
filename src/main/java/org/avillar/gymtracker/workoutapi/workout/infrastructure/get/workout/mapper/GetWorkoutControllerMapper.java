package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workout.model.GetWorkoutResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutControllerMapper {

  GetWorkoutResponseInfrastructure getResponse(
      GetWorkoutResponseApplication getWorkoutResponseApplication);
}
