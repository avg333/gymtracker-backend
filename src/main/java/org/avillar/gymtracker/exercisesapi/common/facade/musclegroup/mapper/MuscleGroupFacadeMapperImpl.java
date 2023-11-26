package org.avillar.gymtracker.exercisesapi.common.facade.musclegroup.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSupGroup;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class MuscleGroupFacadeMapperImpl implements MuscleGroupFacadeMapper {

  @Override
  public List<MuscleGroup> map(final List<MuscleGroupEntity> muscleGroupEntities) {
    if (muscleGroupEntities == null || !Hibernate.isInitialized(muscleGroupEntities)) {
      return Collections.emptyList();
    }

    return muscleGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private List<MuscleSupGroup> mapMuscleSupGroups(
      final Set<MuscleSupGroupEntity> muscleSupGroupEntities) {
    if (muscleSupGroupEntities == null || !Hibernate.isInitialized(muscleSupGroupEntities)) {
      return Collections.emptyList();
    }

    return muscleSupGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private List<MuscleSubGroup> mapMuscleSubGroups(
      final Set<MuscleSubGroupEntity> muscleSubGroupEntities) {
    if (muscleSubGroupEntities == null || !Hibernate.isInitialized(muscleSubGroupEntities)) {
      return Collections.emptyList();
    }

    return muscleSubGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private MuscleGroup map(final MuscleGroupEntity muscleGroupEntity) {
    if (muscleGroupEntity == null) {
      return null;
    }

    return MuscleGroup.builder()
        .id(muscleGroupEntity.getId())
        .name(muscleGroupEntity.getName())
        .description(muscleGroupEntity.getDescription())
        .muscleSupGroups(mapMuscleSupGroups(muscleGroupEntity.getMuscleSupGroups()))
        .muscleSubGroups(mapMuscleSubGroups(muscleGroupEntity.getMuscleSubGroups()))
        .build();
  }

  private MuscleSupGroup map(final MuscleSupGroupEntity muscleSupGroupEntity) {
    if (muscleSupGroupEntity == null || !Hibernate.isInitialized(muscleSupGroupEntity)) {
      return null;
    }

    return MuscleSupGroup.builder()
        .id(muscleSupGroupEntity.getId())
        .name(muscleSupGroupEntity.getName())
        .description(muscleSupGroupEntity.getDescription())
        .build();
  }

  private MuscleSubGroup map(final MuscleSubGroupEntity muscleSupGroupEntity) {
    if (muscleSupGroupEntity == null || !Hibernate.isInitialized(muscleSupGroupEntity)) {
      return null;
    }

    return MuscleSubGroup.builder()
        .id(muscleSupGroupEntity.getId())
        .name(muscleSupGroupEntity.getName())
        .description(muscleSupGroupEntity.getDescription())
        .build();
  }
}
