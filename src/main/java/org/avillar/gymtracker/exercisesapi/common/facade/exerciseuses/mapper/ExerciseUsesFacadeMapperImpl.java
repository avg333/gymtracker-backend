package org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class ExerciseUsesFacadeMapperImpl implements ExerciseUsesFacadeMapper {

  @Override
  public List<ExerciseUses> mapExerciseUsesEntityList(
      final List<ExerciseUsesEntity> exerciseUsesEntities) {
    if (exerciseUsesEntities == null) {
      return null;
    }

    return exerciseUsesEntities.stream().map(this::map).toList();
  }

  @Override
  public List<ExerciseUsesEntity> mapExerciseUsesList(final List<ExerciseUses> exerciseUses) {
    if (exerciseUses == null) {
      return null;
    }

    return exerciseUses.stream().map(this::map).toList();
  }

  private ExerciseUses map(final ExerciseUsesEntity exerciseUsesEntity) {
    if (exerciseUsesEntity == null) {
      return null;
    }

    final ExerciseUses exerciseUses =
        ExerciseUses.builder()
            .id(exerciseUsesEntity.getId())
            .uses(exerciseUsesEntity.getUses())
            .userId(exerciseUsesEntity.getUserId())
            .build();

    if (exerciseUsesEntity.getExercise() != null
        && Hibernate.isInitialized(exerciseUsesEntity.getExercise())) {
      exerciseUses.setExercise(
          Exercise.builder().id(exerciseUsesEntity.getExercise().getId()).build());
    }

    return exerciseUses;
  }

  private ExerciseUsesEntity map(final ExerciseUses exerciseUses) {
    if (exerciseUses == null) {
      return null;
    }

    final ExerciseUsesEntity exerciseUsesEntity = new ExerciseUsesEntity();
    exerciseUsesEntity.setId(exerciseUses.getId());
    exerciseUsesEntity.setUses(exerciseUses.getUses());
    exerciseUsesEntity.setUserId(exerciseUses.getUserId());

    if (exerciseUses.getExercise() != null) {
      final ExerciseEntity exerciseEntity = new ExerciseEntity();
      exerciseEntity.setId(exerciseUses.getExercise().getId());
      exerciseUsesEntity.setExercise(exerciseEntity);
    }

    return exerciseUsesEntity;
  }
}
