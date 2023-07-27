package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GetWorkoutDetailsServiceMapper {

  GetWorkoutDetailsResponseApplication map(Workout workout);
}
