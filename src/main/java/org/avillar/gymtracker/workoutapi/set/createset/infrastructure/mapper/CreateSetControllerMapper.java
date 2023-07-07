package org.avillar.gymtracker.workoutapi.set.createset.infrastructure.mapper;

import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetRequestApplication;
import org.avillar.gymtracker.workoutapi.set.createset.application.model.CreateSetResponseApplication;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSetControllerMapper {

  CreateSetResponse map(CreateSetResponseApplication createSetResponseApplication);

  CreateSetRequestApplication map(CreateSetRequest createSetRequest);
}
