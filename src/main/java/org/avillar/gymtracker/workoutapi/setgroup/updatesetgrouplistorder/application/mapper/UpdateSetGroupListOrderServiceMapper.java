package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model.UpdateSetGroupListOrderResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupListOrderServiceMapper {

  List<UpdateSetGroupListOrderResponseApplication> map(List<SetGroup> setGroups);
}
