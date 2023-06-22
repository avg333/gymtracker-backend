package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.exercisesetgroups.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model.GetExerciseSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.exercisesetgroups.model.GetExerciseSetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseSetGroupsControllerMapper {

  GetExerciseSetGroupsResponseInfrastructure map(
      GetExerciseSetGroupsResponseApplication getExerciseSetGroupsResponseApplication);
}
