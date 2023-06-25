package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypesApplicationResponse;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model.GetLoadTypesResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetLoadTypesControllerMapper {

  GetLoadTypesResponseInfrastructure map(
      GetLoadTypesApplicationResponse getLoadTypesApplicationResponse);

  List<GetLoadTypesResponseInfrastructure> map(
      List<GetLoadTypesApplicationResponse> getLoadTypesApplicationResponses);
}
