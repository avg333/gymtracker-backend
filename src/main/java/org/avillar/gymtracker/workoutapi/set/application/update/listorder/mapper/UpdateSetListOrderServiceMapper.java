package org.avillar.gymtracker.workoutapi.set.application.update.listorder.mapper;

import org.avillar.gymtracker.workoutapi.set.application.update.listorder.model.UpdateSetListOrderResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetListOrderServiceMapper {

  java.util.Set<UpdateSetListOrderResponseApplication.Set> map(java.util.Set<Set> sets);
}
