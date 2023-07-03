package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.model.CopySetGroupsResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CopySetGroupsControllerMapper {

  List<CopySetGroupsResponseInfrastructure> map(
      List<CopySetGroupsResponseApplication> copySetGroupsResponseApplications);
}
