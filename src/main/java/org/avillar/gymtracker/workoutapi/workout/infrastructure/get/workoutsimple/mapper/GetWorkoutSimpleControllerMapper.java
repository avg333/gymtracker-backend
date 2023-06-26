package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.model.GetWorkoutSimpleResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsimple.model.GetWorkoutSimpleResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSimpleControllerMapper {

  GetWorkoutSimpleResponseInfrastructure map(
      GetWorkoutSimpleResponseApplication getWorkoutSimpleResponseApplication);
}
