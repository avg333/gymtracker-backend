package org.avillar.gymtracker.exercisesapi.common.facade.musclesupgroup.mapper;

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
public class MuscleSupGroupFacadeMapperImpl implements MuscleSupGroupFacadeMapper {

  @Override
  public List<MuscleSupGroup> map(final List<MuscleSupGroupEntity> muscleSupGroupEntities) {
    if (muscleSupGroupEntities == null || !Hibernate.isInitialized(muscleSupGroupEntities)) {
      return null;
    }

    return muscleSupGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private List<MuscleGroup> mapMuscleGroups(final Set<MuscleGroupEntity> muscleGroupEntities) {
    if (muscleGroupEntities == null || !Hibernate.isInitialized(muscleGroupEntities)) {
      return null;
    }

    return muscleGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private List<MuscleSubGroup> mapMuscleSubGroups(
      final Set<MuscleSubGroupEntity> muscleSubGroupEntities) {
    if (muscleSubGroupEntities == null || !Hibernate.isInitialized(muscleSubGroupEntities)) {
      return null;
    }

    return muscleSubGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private MuscleSupGroup map(final MuscleSupGroupEntity muscleSupGroupEntity) {
    if (muscleSupGroupEntity == null || !Hibernate.isInitialized(muscleSupGroupEntity)) {
      return null;
    }

    return MuscleSupGroup.builder()
        .id(muscleSupGroupEntity.getId())
        .name(muscleSupGroupEntity.getName())
        .description(muscleSupGroupEntity.getDescription())
        .muscleGroups(mapMuscleGroups(muscleSupGroupEntity.getMuscleGroups()))
        .build();
  }

  private MuscleGroup map(final MuscleGroupEntity muscleGroupEntity) {
    if (muscleGroupEntity == null || !Hibernate.isInitialized(muscleGroupEntity)) {
      return null;
    }

    return MuscleGroup.builder()
        .id(muscleGroupEntity.getId())
        .name(muscleGroupEntity.getName())
        .description(muscleGroupEntity.getDescription())
        .muscleSubGroups(mapMuscleSubGroups(muscleGroupEntity.getMuscleSubGroups()))
        .build();
  }

  private MuscleSubGroup map(final MuscleSubGroupEntity muscleSubGroupEntity) {
    if (muscleSubGroupEntity == null || !Hibernate.isInitialized(muscleSubGroupEntity)) {
      return null;
    }

    return MuscleSubGroup.builder()
        .id(muscleSubGroupEntity.getId())
        .name(muscleSubGroupEntity.getName())
        .description(muscleSubGroupEntity.getDescription())
        .build();
  }
}
