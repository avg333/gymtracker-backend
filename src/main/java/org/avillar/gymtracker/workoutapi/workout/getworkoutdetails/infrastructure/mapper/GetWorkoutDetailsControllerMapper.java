package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutDetailsControllerMapper {

  GetWorkoutDetailsResponse map(
      GetWorkoutDetailsResponseApplication getWorkoutDetailsResponseApplication);
}
