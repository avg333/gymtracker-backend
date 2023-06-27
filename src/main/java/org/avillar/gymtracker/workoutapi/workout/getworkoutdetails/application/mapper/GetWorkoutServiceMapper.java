package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.mapper;

import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutServiceMapper {

  GetWorkoutResponseApplication map(Workout workout);
}
