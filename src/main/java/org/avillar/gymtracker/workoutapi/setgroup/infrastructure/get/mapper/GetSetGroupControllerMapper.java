package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.model.GetSetGroupResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupControllerMapper {

  GetSetGroupResponseInfrastructure getResponse(
      GetSetGroupResponseApplication getSetGroupResponseApplication);

  List<GetSetGroupResponseInfrastructure> getResponse(
      List<GetSetGroupResponseApplication> getSetGroupResponsApplications);
}
