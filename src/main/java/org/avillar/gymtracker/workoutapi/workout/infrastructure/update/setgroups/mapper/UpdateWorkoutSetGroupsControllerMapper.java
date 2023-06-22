package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.model.UpdateWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model.UpdateWorkoutSetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateWorkoutSetGroupsControllerMapper {

  UpdateWorkoutSetGroupsResponseInfrastructure map(
      UpdateWorkoutSetGroupsResponseApplication updateWorkoutSetGroupsResponseApplication);
}
