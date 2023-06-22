package org.avillar.gymtracker.workoutapi.workout.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostWorkoutControllerMapper {

  PostWorkoutResponseInfrastructure map(
      PostWorkoutResponseApplication postWorkoutResponseApplication);

  PostWorkoutRequestApplication map(
      PostWorkoutRequestInfrastructure postWorkoutRequestInfrastructure);
}
