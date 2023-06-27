package org.avillar.gymtracker.workoutapi.workout.getworkout.application.mapper;

import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkout.application.model.GetWorkoutSimpleResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSimpleServiceMapper {

  GetWorkoutSimpleResponseApplication map(Workout workout);
}
