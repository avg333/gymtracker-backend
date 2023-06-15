package org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.mapper;

import java.util.Set;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponse;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderServiceMapper {

  Set<UpdateSetGroupListOrderResponse.SetGroup> updateResponse(Set<SetGroup> setGroups);
}
