package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetExerciseSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetExerciseSetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseSetGroupsControllerMapper {

  GetExerciseSetGroupsResponseInfrastructure map(
      GetExerciseSetGroupsResponseApplication getExerciseSetGroupsResponseApplication);
}
