package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateWorkoutControllerMapper {

  CreateWorkoutResponseInfrastructure map(
      CreateWorkoutResponseApplication createWorkoutResponseApplication);

  CreateWorkoutRequestApplication map(
      CreateWorkoutRequestInfrastructure createWorkoutRequestInfrastructure);
}
