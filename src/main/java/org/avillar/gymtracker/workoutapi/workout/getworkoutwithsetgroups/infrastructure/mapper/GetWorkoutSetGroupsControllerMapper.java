package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.infrastructure.model.GetWorkoutSetGroupsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetWorkoutSetGroupsControllerMapper {

  GetWorkoutSetGroupsResponse map(Workout workout);
}
