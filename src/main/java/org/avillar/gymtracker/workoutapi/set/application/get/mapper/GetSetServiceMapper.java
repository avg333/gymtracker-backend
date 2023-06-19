package org.avillar.gymtracker.workoutapi.set.application.get.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetServiceMapper {

  GetSetResponseApplication getResponse(Set set);
}
