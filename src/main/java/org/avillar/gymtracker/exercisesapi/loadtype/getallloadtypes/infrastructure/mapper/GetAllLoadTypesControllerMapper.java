package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetAllLoadTypesControllerMapper {

  List<GetAllLoadTypesResponse> map(List<LoadType> loadTypes);
}
