package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model.GetLoadTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetLoadTypeControllerMapper {

  GetLoadTypeResponse getResponse(
      org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypeResponse
          getLoadTypeResponse);

  List<GetLoadTypeResponse> getResponse(
      List<org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypeResponse>
          getLoadTypeResponse);
}
