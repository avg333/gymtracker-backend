package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllLoadTypesControllerMapper {

  GetAllLoadTypesResponse map(
      GetAllLoadTypesResponseApplication getAllLoadTypesResponseApplication);

  List<GetAllLoadTypesResponse> map(
      List<GetAllLoadTypesResponseApplication> getAllLoadTypesResponseApplications);
}
