package org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsetgroups.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSetGroupsControllerMapper {

  GetWorkoutSetGroupsResponseInfrastructure map(
      GetWorkoutSetGroupsResponseApplication getWorkoutSetGroupsResponseApplication);
}
