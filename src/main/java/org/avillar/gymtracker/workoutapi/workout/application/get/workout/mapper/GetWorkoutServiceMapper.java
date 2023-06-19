package org.avillar.gymtracker.workoutapi.workout.application.get.workout.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutServiceMapper {

  GetWorkoutResponseApplication getResponse(Workout workout);
}
