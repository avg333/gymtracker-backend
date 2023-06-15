package org.avillar.gymtracker.workoutapi.workout.application.post.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequest;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponse;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostWorkoutServiceMapper {

  PostWorkoutResponse postResponse(Workout workout);

  Workout postRequest(PostWorkoutRequest postWorkoutRequest);
}
