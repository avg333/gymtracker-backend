package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.UpdateWorkoutSetGroupsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateWorkoutSetGroupsServiceMapper {

  List<UpdateWorkoutSetGroupsResponseApplication.SetGroup> map(List<SetGroup> setGroups);
}
