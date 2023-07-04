package org.avillar.gymtracker.workoutapi.setgroup.movesets.application.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.movesets.application.model.UpdateSetGroupSetsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupSetsServiceMapper {

  List<UpdateSetGroupSetsResponseApplication.Set> map(List<Set> sets);
}
