package org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;

public interface MuscleSupGroupFacadeMapper {

  List<MuscleSupGroup> map(List<MuscleSupGroupEntity> muscleSupGroupEntities);
}
