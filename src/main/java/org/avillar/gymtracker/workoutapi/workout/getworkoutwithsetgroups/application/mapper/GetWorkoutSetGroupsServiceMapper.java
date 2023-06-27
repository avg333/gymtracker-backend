package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model.GetWorkoutSetGroupsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSetGroupsServiceMapper {

  GetWorkoutSetGroupsResponseApplication map(Workout workout);
}
