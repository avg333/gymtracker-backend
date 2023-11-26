package org.avillar.gymtracker.exercisesapi.common.facade.musclesubgroup.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleSubGroup;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class MuscleSubGroupFacadeMapperImpl implements MuscleSubGroupFacadeMapper {

  @Override
  public List<MuscleSubGroup> map(final List<MuscleSubGroupEntity> muscleSubGroupEntities) {
    if (muscleSubGroupEntities == null || !Hibernate.isInitialized(muscleSubGroupEntities)) {
      return Collections.emptyList();
    }

    return muscleSubGroupEntities.stream().map(this::map).collect(Collectors.toList());
  }

  private MuscleSubGroup map(final MuscleSubGroupEntity muscleSubGroupEntity) {
    if (muscleSubGroupEntity == null || !Hibernate.isInitialized(muscleSubGroupEntity)) {
      return null;
    }

    return MuscleSubGroup.builder()
        .id(muscleSubGroupEntity.getId())
        .name(muscleSubGroupEntity.getName())
        .description(muscleSubGroupEntity.getDescription())
        .muscleGroup(
            Optional.ofNullable(muscleSubGroupEntity.getMuscleGroup())
                .map(mge -> MuscleGroup.builder().id(mge.getId()).build())
                .orElse(null))
        .build();
  }
}
