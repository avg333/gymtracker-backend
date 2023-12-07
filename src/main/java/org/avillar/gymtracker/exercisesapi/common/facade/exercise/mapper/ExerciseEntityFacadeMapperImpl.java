package org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class ExerciseEntityFacadeMapperImpl implements ExerciseEntityFacadeMapper {

  @Override
  public List<Exercise> map(List<ExerciseEntity> exerciseEntities) {
    return Optional.ofNullable(exerciseEntities).orElse(Collections.emptyList()).stream()
        .map(this::map)
        .collect(java.util.stream.Collectors.toList());
  }

  @Override
  public Exercise map(ExerciseEntity exerciseEntity) {
    if (exerciseEntity == null) {
      return null;
    }

    return Exercise.builder()
        .id(exerciseEntity.getId())
        .name(exerciseEntity.getName())
        .description(exerciseEntity.getDescription())
        .accessType(exerciseEntity.getAccessType())
        .owner(exerciseEntity.getOwner())
        .exerciseUses(mapExerciseUses(exerciseEntity.getExerciseUses()))
        .unilateral(exerciseEntity.getUnilateral())
        .loadType(mapLoadType(exerciseEntity.getLoadType()))
        .muscleSubGroups(mapMuscleSubGroups(exerciseEntity.getMuscleSubGroups()))
        .muscleGroupExercises(mapMuscleGroupExercises(exerciseEntity.getMuscleGroupExercises()))
        .build();
  }

  private List<ExerciseUses> mapExerciseUses(Collection<ExerciseUsesEntity> exerciseUsesEntities) {
    if (exerciseUsesEntities == null || !Hibernate.isInitialized(exerciseUsesEntities)) {
      return null;
    }

    return exerciseUsesEntities.stream().map(this::mapExerciseUses).collect(Collectors.toList());
  }

  private ExerciseUses mapExerciseUses(ExerciseUsesEntity exerciseUsesEntity) {
    if (exerciseUsesEntity == null || !Hibernate.isInitialized(exerciseUsesEntity)) {
      return null;
    }

    return ExerciseUses.builder()
        .id(exerciseUsesEntity.getId())
        .uses(exerciseUsesEntity.getUses())
        .userId(exerciseUsesEntity.getUserId())
        .build();
  }

  private LoadType mapLoadType(LoadTypeEntity loadTypeEntity) {
    if (loadTypeEntity == null || !Hibernate.isInitialized(loadTypeEntity)) {
      return null;
    }

    return LoadType.builder()
        .id(loadTypeEntity.getId())
        .name(loadTypeEntity.getName())
        .description(loadTypeEntity.getDescription())
        .build();
  }

  private List<MuscleSubGroup> mapMuscleSubGroups(
      Collection<MuscleSubGroupEntity> muscleSubGroupEntities) {
    if (muscleSubGroupEntities == null || !Hibernate.isInitialized(muscleSubGroupEntities)) {
      return null;
    }

    return muscleSubGroupEntities.stream()
        .map(this::mapMuscleSubGroup)
        .collect(Collectors.toList());
  }

  private MuscleSubGroup mapMuscleSubGroup(MuscleSubGroupEntity muscleSubGroupEntity) {
    if (muscleSubGroupEntity == null || !Hibernate.isInitialized(muscleSubGroupEntity)) {
      return null;
    }

    return MuscleSubGroup.builder()
        .id(muscleSubGroupEntity.getId())
        .name(muscleSubGroupEntity.getName())
        .description(muscleSubGroupEntity.getDescription())
        .build();
  }

  private List<MuscleGroupExercise> mapMuscleGroupExercises(
      Collection<MuscleGroupExerciseEntity> muscleGroupExerciseEntities) {
    if (muscleGroupExerciseEntities == null
        || !Hibernate.isInitialized(muscleGroupExerciseEntities)) {
      return null;
    }

    return muscleGroupExerciseEntities.stream()
        .map(this::mapMuscleGroupExercise)
        .collect(Collectors.toList());
  }

  private MuscleGroupExercise mapMuscleGroupExercise(
      MuscleGroupExerciseEntity muscleGroupExerciseEntity) {
    if (muscleGroupExerciseEntity == null || !Hibernate.isInitialized(muscleGroupExerciseEntity)) {
      return null;
    }

    return MuscleGroupExercise.builder()
        .id(muscleGroupExerciseEntity.getId())
        .weight(muscleGroupExerciseEntity.getWeight())
        .muscleGroup(mapMuscleGroup(muscleGroupExerciseEntity.getMuscleGroup()))
        .build();
  }

  private MuscleGroup mapMuscleGroup(MuscleGroupEntity muscleGroupEntity) {
    if (muscleGroupEntity == null || !Hibernate.isInitialized(muscleGroupEntity)) {
      return null;
    }

    return MuscleGroup.builder()
        .id(muscleGroupEntity.getId())
        .name(muscleGroupEntity.getName())
        .description(muscleGroupEntity.getDescription())
        .build();
  }
}
