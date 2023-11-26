package org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper;

import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.springframework.stereotype.Component;

@Component
public class ExerciseFacadeMapperImpl implements ExerciseFacadeMapper {

  @Override
  public ExerciseEntity map(Exercise exercise) {
    if (exercise == null) {
      return null;
    }

    final ExerciseEntity exerciseEntity = new ExerciseEntity();
    exerciseEntity.setId(exercise.getId());
    exerciseEntity.setName(exercise.getName());
    exerciseEntity.setDescription(exercise.getDescription());
    exerciseEntity.setAccessType(exercise.getAccessType());
    exerciseEntity.setOwner(exercise.getOwner());
    exerciseEntity.setUnilateral(exercise.getUnilateral());

    if (exercise.getLoadType() != null) {
      exerciseEntity.setLoadType(new LoadTypeEntity().withId(exercise.getLoadType().getId()));
    }

    // TODO Finish mapping

    return exerciseEntity;
  }
}
