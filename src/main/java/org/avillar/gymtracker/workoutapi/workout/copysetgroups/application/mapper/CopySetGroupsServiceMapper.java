package org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CopySetGroupsServiceMapper {

  List<CopySetGroupsResponseApplication> map(List<SetGroup> setGroups);
}
