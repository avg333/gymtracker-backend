package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupControllerMapper {

  org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.model.GetSetGroupResponse
      getResponse(GetSetGroupResponse getSetGroupResponse);

  List<org.avillar.gymtracker.workoutapi.setgroup.infrastructure.get.model.GetSetGroupResponse>
      getResponse(List<GetSetGroupResponse> getSetGroupResponses);
}
