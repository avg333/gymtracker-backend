package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CreateWorkoutControllerMapper {

  CreateWorkoutResponse map(Workout workout);

  Workout map(CreateWorkoutRequest createWorkoutRequest);
}
