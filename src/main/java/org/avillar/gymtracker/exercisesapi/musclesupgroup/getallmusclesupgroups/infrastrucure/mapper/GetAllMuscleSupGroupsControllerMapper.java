package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllMuscleSupGroupsControllerMapper {

  List<GetAllMuscleSupGroupsResponseInfrastructure> map(
      List<GetAllMuscleSupGroupsResponseApplication> getAllMuscleSupGroupsResponseApplications);
}
