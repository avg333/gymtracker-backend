package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetWorkoutControllerMapper {

  GetWorkoutResponse map(Workout workout);
}
