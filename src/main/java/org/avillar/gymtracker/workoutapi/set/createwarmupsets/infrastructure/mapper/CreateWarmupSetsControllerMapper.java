package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CreateWarmupSetsControllerMapper {

  List<CreateWarmupSetsResponse> map(List<Set> sets);
}
