package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsimple.model.GetWorkoutSimpleResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSimpleServiceMapper {

  GetWorkoutSimpleResponseApplication map(Workout workout);
}
