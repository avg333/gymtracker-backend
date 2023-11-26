package org.avillar.gymtracker.exercisesapi.common.facade.exercise;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper.ExerciseEntityFacadeMapper;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper.ExerciseFacadeMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@RequiredArgsConstructor
public class ExerciseFacadeImpl implements ExerciseFacade {

  private final ExerciseDao exerciseDao;
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
        exerciseDao.getAllFullExercises(
            userId,
            AccessTypeEnum.PRIVATE,
            AccessTypeEnum.PUBLIC,
            StringUtils.isBlank(filter.getName()) ? null : filter.getName(),
            StringUtils.isBlank(filter.getDescription()) ? null : filter.getDescription(),
            filter.getUnilateral(),
            CollectionUtils.isEmpty(filter.getLoadTypeIds()),
            CollectionUtils.isEmpty(filter.getLoadTypeIds())
                ? Collections.emptyList()
                : filter.getLoadTypeIds(),
            CollectionUtils.isEmpty(filter.getMuscleSubGroupIds()),
            CollectionUtils.isEmpty(filter.getMuscleSubGroupIds())
                ? Collections.emptyList()
                : filter.getMuscleSubGroupIds(),
            CollectionUtils.isEmpty(filter.getMuscleSupGroupIds()),
            CollectionUtils.isEmpty(filter.getMuscleSupGroupIds())
                ? Collections.emptyList()
                : filter.getMuscleSupGroupIds(),
            CollectionUtils.isEmpty(filter.getMuscleGroupIds()),
            CollectionUtils.isEmpty(filter.getMuscleGroupIds())
                ? Collections.emptyList()
                : filter.getMuscleGroupIds()));
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
