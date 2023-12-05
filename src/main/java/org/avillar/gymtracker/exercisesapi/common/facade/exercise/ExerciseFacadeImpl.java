package org.avillar.gymtracker.exercisesapi.common.facade.exercise;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.CriteriaExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper.ExerciseEntityFacadeMapper;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper.ExerciseFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ExerciseFacadeImpl implements ExerciseFacade {

  private final ExerciseDao exerciseDao;
  private final CriteriaExerciseDao criteriaExerciseDao;
  private final ExerciseFacadeMapper exerciseFacadeMapper;
  private final ExerciseEntityFacadeMapper exerciseEntityFacadeMapper;

  @Override
  public Exercise getExerciseById(UUID exerciseId) throws ExerciseNotFoundException {
    return exerciseEntityFacadeMapper.map(
        exerciseDao
            .findById(exerciseId)
            .orElseThrow(() -> new ExerciseNotFoundException(exerciseId)));
  }

  @Override
  public List<Exercise> getExercisesByFilter(UUID userId, GetExercisesFilter filter) {
    return exerciseEntityFacadeMapper.map(
        criteriaExerciseDao.getAllFullExercises(exerciseFacadeMapper.map(userId, filter)));
  }

  @Override
  public List<Exercise> getExercisesByIds(Collection<UUID> exerciseIds) {
    return exerciseEntityFacadeMapper.map(exerciseDao.findAllById(new HashSet<>(exerciseIds)));
  }

  @Override
  public Exercise getFullExerciseById(UUID exerciseId) throws ExerciseNotFoundException {
    return exerciseEntityFacadeMapper.map(
        exerciseDao.getFullExerciseByIds(Set.of(exerciseId)).stream()
            .findAny()
            .orElseThrow(() -> new ExerciseNotFoundException(exerciseId)));
  }

  @Override
  public Exercise getExerciseWithMuscleGroupExUses(UUID exerciseId)
      throws ExerciseNotFoundException {
    return exerciseEntityFacadeMapper.map(
        exerciseDao.getExerciseByIdWithMuscleGroupEx(exerciseId).stream()
            .findAny()
            .orElseThrow(() -> new ExerciseNotFoundException(exerciseId)));
  }

  @Override
  public void deleteExerciseById(UUID exerciseId) {
    exerciseDao.deleteById(exerciseId);
  }
}
