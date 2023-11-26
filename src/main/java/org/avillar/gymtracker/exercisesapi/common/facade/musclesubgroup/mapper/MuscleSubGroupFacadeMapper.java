package org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;

public interface MuscleSubGroupFacadeMapper {

  List<MuscleSubGroup> map(List<MuscleSubGroupEntity> muscleSubGroupEntities);
}
