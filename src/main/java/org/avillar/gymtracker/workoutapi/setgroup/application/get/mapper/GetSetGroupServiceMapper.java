package org.avillar.gymtracker.workoutapi.setgroup.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.setgroup.application.get.model.GetSetGroupResponseApplication;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetGroupServiceMapper {

  GetSetGroupResponseApplication getResponse(SetGroup setGroup);

  List<GetSetGroupResponseApplication> getResponse(List<SetGroup> setGroups);
}
