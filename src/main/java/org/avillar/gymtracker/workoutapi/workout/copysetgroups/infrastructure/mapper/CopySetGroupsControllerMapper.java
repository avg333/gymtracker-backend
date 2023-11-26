package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.application.CopySetGroupsServiceImpl.CopySetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestDto;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CopySetGroupsControllerMapper {

  List<CopySetGroupsResponseDto> map(List<SetGroup> copySetGroupsResponses);

  CopySetGroupsRequest map(CopySetGroupsRequestDto copySetGroupsRequestDto);
}
