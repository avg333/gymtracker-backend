package org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;

public interface ExerciseUsesFacadeMapper {

  List<ExerciseUses> mapExerciseUsesEntityList(List<ExerciseUsesEntity> exerciseUsesEntities);

  List<ExerciseUsesEntity> mapExerciseUsesList(List<ExerciseUses> exerciseUses);
}
