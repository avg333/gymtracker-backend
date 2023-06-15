package org.avillar.gymtracker.workoutapi.setgroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponse;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupServiceMapper {

  GetSetGroupResponse getResponse(SetGroup setGroup);

  List<GetSetGroupResponse> getResponse(List<SetGroup> setGroups);
}
