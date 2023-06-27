package org.avillar.gymtracker.workoutapi.workout.createworkout.application.mapper;

import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateWorkoutServiceMapper {

  CreateWorkoutResponseApplication map(Workout workout);

  Workout map(CreateWorkoutRequestApplication createWorkoutRequestApplication);
}
