package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.application.model.UpdateSetListOrderResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderServiceMapper {

  java.util.Set<UpdateSetListOrderResponseApplication.Set> map(java.util.Set<Set> sets);
}
