package org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model.GetExerciseSetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseSetGroupsServiceMapper {
  List<GetExerciseSetGroupsResponseApplication.SetGroup> map(List<SetGroup> setGroups);
}
