package org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.mapper;

import java.util.Set;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.listorder.model.UpdateSetGroupListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderServiceMapper {

  Set<UpdateSetGroupListOrderResponseApplication.SetGroup> map(Set<SetGroup> setGroups);
}
