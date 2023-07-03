package org.avillar.gymtracker.workoutapi.workout.getworkout.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutServiceMapper {

  GetWorkoutResponseApplication map(Workout workout);
}
