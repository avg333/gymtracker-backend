package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetAllLoadTypesServiceMapper {

  List<GetAllLoadTypesResponseApplication> map(List<LoadType> loadType);
}
