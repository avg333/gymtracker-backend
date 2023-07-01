package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllLoadTypesControllerMapper {

  GetAllLoadTypesResponseInfrastructure map(
      GetAllLoadTypesResponseApplication getAllLoadTypesResponseApplication);

  List<GetAllLoadTypesResponseInfrastructure> map(
      List<GetAllLoadTypesResponseApplication> getAllLoadTypesResponseApplications);
}
