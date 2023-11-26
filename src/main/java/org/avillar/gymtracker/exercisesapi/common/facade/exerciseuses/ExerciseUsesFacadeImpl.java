package org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseUsesDao;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.mapper.ExerciseUsesFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExerciseUsesFacadeImpl implements ExerciseUsesFacade {

  private final ExerciseUsesDao exerciseUsesDao;
  private final ExerciseUsesFacadeMapper exerciseUsesFacadeMapper;

  @Override
  public List<ExerciseUses> getExerciseUsesByExerciseIdAndUserId(
      final List<UUID> exerciseIds, final UUID userId) {
    return exerciseUsesFacadeMapper.mapExerciseUsesEntityList(
        exerciseUsesDao.getExerciseUsesByExerciseIdAndUserId(exerciseIds, userId));
  }

  @Override
  public List<ExerciseUses> saveAllExerciseUses(final List<ExerciseUses> exerciseUses) {
    return exerciseUsesFacadeMapper.mapExerciseUsesEntityList(
        exerciseUsesDao.saveAll(exerciseUsesFacadeMapper.mapExerciseUsesList(exerciseUses)));
  }
}
