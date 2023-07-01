package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupSetsServiceMapper {

  List<UpdateSetGroupSetsResponseApplication.Set> map(List<Set> sets);
}