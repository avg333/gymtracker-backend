package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetExerciseSetGroupsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetExerciseSetGroupsServiceMapper {
  List<GetExerciseSetGroupsResponseApplication.SetGroup> map(List<SetGroup> setGroups);
}
