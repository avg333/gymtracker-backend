package org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.mapper;

import org.avillar.gymtracker.workoutapi.workout.application.get.workoutsetgroups.model.GetWorkoutSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetWorkoutSetGroupsServiceMapper {

  GetWorkoutSetGroupsResponseApplication map(Workout workout);
}
