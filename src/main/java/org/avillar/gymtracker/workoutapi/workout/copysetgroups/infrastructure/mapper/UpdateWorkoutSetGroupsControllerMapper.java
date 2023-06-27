package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.UpdateWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.UpdateWorkoutSetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateWorkoutSetGroupsControllerMapper {

  UpdateWorkoutSetGroupsResponseInfrastructure map(
      UpdateWorkoutSetGroupsResponseApplication updateWorkoutSetGroupsResponseApplication);
}
