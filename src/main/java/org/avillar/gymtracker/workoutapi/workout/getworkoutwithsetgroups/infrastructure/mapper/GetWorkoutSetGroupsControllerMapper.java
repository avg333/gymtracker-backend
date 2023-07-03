package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSetGroupsControllerMapper {

  GetWorkoutSetGroupsResponseInfrastructure map(
      GetWorkoutSetGroupsResponseApplication getWorkoutSetGroupsResponseApplication);
}
