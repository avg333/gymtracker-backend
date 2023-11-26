package org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;

public interface MuscleGroupFacadeMapper {

  List<MuscleGroup> map(List<MuscleGroupEntity> muscleGroupEntities);
}
