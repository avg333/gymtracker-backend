package org.avillar.gymtracker.workoutapi.workout.infrastructure.post.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostWorkoutControllerMapper {

  org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutResponse
      postResponse(PostWorkoutResponse postWorkoutResponse);

  PostWorkoutRequest postRequest(
      org.avillar.gymtracker.workoutapi.workout.infrastructure.post.model.PostWorkoutRequest
          postWorkoutRequest);
}
