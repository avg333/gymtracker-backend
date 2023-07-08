package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateWorkoutControllerMapper {

  CreateWorkoutResponse map(CreateWorkoutResponseApplication createWorkoutResponseApplication);

  CreateWorkoutRequestApplication map(CreateWorkoutRequest createWorkoutRequest);
}
