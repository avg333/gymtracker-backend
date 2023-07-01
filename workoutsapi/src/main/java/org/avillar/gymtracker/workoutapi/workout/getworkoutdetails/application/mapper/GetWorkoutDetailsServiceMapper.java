package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutDetailsServiceMapper {

  GetWorkoutDetailsResponseApplication map(Workout workout);
}
