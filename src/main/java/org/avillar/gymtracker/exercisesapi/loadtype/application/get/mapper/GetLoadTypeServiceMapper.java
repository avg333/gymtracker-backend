package org.avillar.gymtracker.exercisesapi.loadtype.application.get.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypeResponse;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GetLoadTypeServiceMapper {

  GetLoadTypeResponse getResponse(LoadType loadType);

  List<GetLoadTypeResponse> getResponse(List<LoadType> loadType);
}
