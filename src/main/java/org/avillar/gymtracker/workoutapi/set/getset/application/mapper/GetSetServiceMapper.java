package org.avillar.gymtracker.workoutapi.set.getset.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.getset.application.model.GetSetResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetSetServiceMapper {

  GetSetResponseApplication map(Set set);
}
