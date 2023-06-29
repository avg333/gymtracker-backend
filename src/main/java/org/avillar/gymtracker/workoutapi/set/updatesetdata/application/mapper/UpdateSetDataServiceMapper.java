package org.avillar.gymtracker.workoutapi.set.updatesetdata.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.application.model.UpdateSetDataResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateSetDataServiceMapper {

  UpdateSetDataResponseApplication map(Set set);
}
