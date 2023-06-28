package org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.application.get.newsetdata.model.GetNewSetDataResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetNewSetDataServiceMapper {

  GetNewSetDataResponseApplication map(Set set);
}
