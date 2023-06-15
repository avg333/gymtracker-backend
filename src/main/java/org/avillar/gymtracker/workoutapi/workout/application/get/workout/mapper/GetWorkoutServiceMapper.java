package org.avillar.gymtracker.workoutapi.workout.application.get.workout.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponse;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutServiceMapper {

  GetWorkoutResponse getResponse(Workout workout);
}
