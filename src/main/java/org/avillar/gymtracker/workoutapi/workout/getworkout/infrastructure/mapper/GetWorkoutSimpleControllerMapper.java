package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutSimpleResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutSimpleResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSimpleControllerMapper {

  GetWorkoutSimpleResponseInfrastructure map(
      GetWorkoutSimpleResponseApplication getWorkoutSimpleResponseApplication);
}
