package org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.model.UpdateWorkoutSetGroupsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateWorkoutSetGroupsServiceMapper {

  List<UpdateWorkoutSetGroupsResponseApplication.SetGroup> map(List<SetGroup> setGroups);
}
