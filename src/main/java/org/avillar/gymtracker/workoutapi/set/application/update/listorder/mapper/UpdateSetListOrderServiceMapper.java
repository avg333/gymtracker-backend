package org.avillar.gymtracker.workoutapi.set.application.update.listorder.mapper;

import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponse;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderServiceMapper {

  java.util.Set<UpdateSetListOrderResponse.Set> updateResponse(java.util.Set<Set> sets);
}
