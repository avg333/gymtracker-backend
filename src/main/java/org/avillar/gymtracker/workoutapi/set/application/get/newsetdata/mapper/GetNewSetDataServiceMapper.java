package org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.mapper;

import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetNewSetDataServiceMapper {

  GetNewSetDataResponseApplication getResponse(Set set);
}
