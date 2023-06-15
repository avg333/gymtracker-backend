package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.mapper;

import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model.UpdateWorkoutSetGroupsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateWorkoutSetGroupsControllerMapper {

  UpdateWorkoutSetGroupsResponse updateResponse(
      org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.model
              .UpdateWorkoutSetGroupsResponse
          setGroups);
}
