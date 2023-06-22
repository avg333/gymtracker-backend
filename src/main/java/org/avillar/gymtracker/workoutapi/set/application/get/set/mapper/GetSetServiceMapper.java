package org.avillar.gymtracker.workoutapi.set.application.get.set.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.set.model.GetSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetServiceMapper {

  GetSetResponseApplication map(Set set);
}
