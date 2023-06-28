package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.mapper;

import java.util.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderServiceMapper {

  Set<UpdateSetGroupListOrderResponseApplication.SetGroup> map(Set<SetGroup> setGroups);
}
