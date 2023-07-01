package org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.mapper;

import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.application.model.GetNewSetDataResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetNewSetDataServiceMapper {

  GetNewSetDataResponseApplication map(Set set);
}
