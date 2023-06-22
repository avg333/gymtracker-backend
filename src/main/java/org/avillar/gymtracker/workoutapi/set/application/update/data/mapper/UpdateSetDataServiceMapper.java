package org.avillar.gymtracker.workoutapi.set.application.update.data.mapper;

import org.avillar.gymtracker.workoutapi.set.application.update.data.model.UpdateSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetDataServiceMapper {

  UpdateSetDataResponseApplication map(Set set);
}
