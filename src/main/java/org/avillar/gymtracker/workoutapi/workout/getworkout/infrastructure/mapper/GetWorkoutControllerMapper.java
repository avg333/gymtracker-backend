package org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutControllerMapper {

  GetWorkoutResponse map(GetWorkoutResponseApplication getWorkoutResponseApplication);
}
