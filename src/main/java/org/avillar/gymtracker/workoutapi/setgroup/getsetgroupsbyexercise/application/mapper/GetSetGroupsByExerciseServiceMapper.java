package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model.GetSetGroupsByExerciseResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupsByExerciseServiceMapper {

  List<GetSetGroupsByExerciseResponseApplication> map(List<SetGroup> setGroups);
}
