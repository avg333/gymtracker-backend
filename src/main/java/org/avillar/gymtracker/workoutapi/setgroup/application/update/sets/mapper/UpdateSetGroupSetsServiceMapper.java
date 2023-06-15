package org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.sets.model.UpdateSetGroupSetsResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetGroupSetsServiceMapper {

  List<UpdateSetGroupSetsResponse.Set> updateResponse(List<Set> sets);
}
