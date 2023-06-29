package org.avillar.gymtracker.workoutapi.set.createset.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSetServiceMapper {

  CreateSetResponseApplication map(Set set);

  Set map(CreateSetRequestApplication createSetRequestApplication);
}
