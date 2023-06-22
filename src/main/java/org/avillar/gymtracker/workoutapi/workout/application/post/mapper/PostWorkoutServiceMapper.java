package org.avillar.gymtracker.workoutapi.workout.application.post.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.application.post.model.PostWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostWorkoutServiceMapper {

  PostWorkoutResponseApplication map(Workout workout);

  Workout map(PostWorkoutRequestApplication postWorkoutRequestApplication);
}
