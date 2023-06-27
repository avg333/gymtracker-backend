package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutControllerMapper {

  GetWorkoutResponseInfrastructure map(GetWorkoutResponseApplication getWorkoutResponseApplication);
}
